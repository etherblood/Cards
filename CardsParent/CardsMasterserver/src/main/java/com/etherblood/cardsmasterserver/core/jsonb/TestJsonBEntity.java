package com.etherblood.cardsmasterserver.core.jsonb;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
 *
 * @author Philipp
 */
//@TypeDefs({@TypeDef(name = "jsonb", typeClass = JSONBUserType.class)})
@TypeDef(name = "jsonb", typeClass = JSONBUserType.class, parameters = {
  @Parameter(name = JSONBUserType.CLASS,
      value = "com.etherblood.cardsmasterserver.core.jsonb.TestJsonBEntity")})
@Entity
public class TestJsonBEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Type(type = "jsonb")
    private Set<JSONBCard> bar;
    
//    private List<String> foo;

    public Set<JSONBCard> getBar() {
        return bar;
    }

    public void setBar(Set<JSONBCard> bar) {
        this.bar = bar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public List<String> getFoo() {
//        return foo;
//    }
//
//    public void setFoo(List<String> foo) {
//        this.foo = foo;
//    }
}
