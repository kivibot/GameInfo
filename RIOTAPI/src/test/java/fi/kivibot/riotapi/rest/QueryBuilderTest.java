package fi.kivibot.riotapi.rest;

import java.util.HashMap;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Nicklas
 */
public class QueryBuilderTest {

    public QueryBuilderTest() {
    }

    @Test
    public void testAppendWithoutParams() {
        QueryBuilder qb = new QueryBuilder();
        qb.append("test");
        assertThat(qb.build(), is("test"));
    }

    @Test
    public void testAppendEncoded() {
        QueryBuilder qb = new QueryBuilder();
        qb.appendEncoded("%%%");
        assertThat(qb.build(), is("%25%25%25"));
    }

    @Test
    public void testAppendWithParams() {
        QueryBuilder qb = new QueryBuilder();
        qb.append("aaa{0}bbb{1}ccc{0}ddd", "%%%", "WWW");
        assertThat(qb.build(), is("aaa%25%25%25bbbWWWccc%25%25%25ddd"));
    }
    
    @Test
    public void testAppendWithNamedParams(){
        QueryBuilder qb = new QueryBuilder();
        HashMap map = new HashMap();
        map.put("qwe", "%%%");
        map.put("WWW", "WWW");
        qb.append("aaa{WWW}bbb{qwe}ccc{WWW}ddd", map);
        assertThat(qb.build(), is("aaaWWWbbb%25%25%25cccWWWddd"));        
    }

}
