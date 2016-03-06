/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.etherblood.cardsmasterserver.core.jsonb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.util.ReflectHelper;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

/**
 * A {@link UserType} that persists objects as JSONB.
 * <p>
 * Unlike the default JPA object mapping, {@code JSONBUserType} can also be used
 * for properties that do not implement {@link Serializable}.
 * <p>
 * Users intending to use this type for mutable non-
 * <code>Collection</code> objects should override
 * {@link #deepCopyValue(Object)} to correctly return a
 * <u>copy</u> of the object.
 */
public class JSONBUserType extends CollectionUserType implements ParameterizedType {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    private static final String JSONB_TYPE = "jsonb";
    public static final String CLASS = "CLASS";
    private Class returnedClass;

    @Override
    public Class<Object> returnedClass() {
        return Object.class;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session, Object owner) throws HibernateException, SQLException {
        final String json = resultSet.getString(names[0]);
        return json == null
                ? null
                : GSON.fromJson(json, returnedClass);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) throws HibernateException, SQLException {
        final String json = value == null
                ? null
                : GSON.toJson(value);
        // otherwise PostgreSQL won't recognize the type
        PGobject pgo = new PGobject();
        pgo.setType(JSONB_TYPE);
        pgo.setValue(json);
        st.setObject(index, pgo);
    }

    @Override
    protected Object deepCopyValue(Object value) {
        return value;
    }

    @Override
    public void setParameterValues(Properties parameters) {
        final String clazz = (String) parameters.get(CLASS);
        try {
            returnedClass = ReflectHelper.classForName(clazz, getClass());
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Class: " + clazz + " is not a known class type.", e);
        }
    }
}