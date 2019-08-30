package laurencewarne.componentlookup;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LookupSubscriptionTest {

    private LookupSubscription<ComponentA, String> sub;
    @Mock
    private ComponentMapper<ComponentA> m;
    private ComponentA c1, c2, c3, c4;
    private int id1 = 1, id2 = 2, id3 = 3, id4 = 4;

    @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);
	sub = new LookupSubscription<>(m, (c) -> c.name);
	c1 = new ComponentA();
	c1.name = "dave";
	c2 = new ComponentA();
	c2.name = "steve";
	c3 = new ComponentA();
	c3.name = "greg";
	c4 = new ComponentA();
	c4.name = "stew";
	when(m.get(id1)).thenReturn(c1);
	when(m.get(id2)).thenReturn(c2);
	when(m.get(id3)).thenReturn(c3);
	when(m.get(id4)).thenReturn(c4);
    }

    @Test
    public void testCanInsertAllEntities() {
	IntBag bag = new IntBag();
	bag.add(id1);
	bag.add(id2);
	bag.add(id3);
	bag.add(id4);
	sub.inserted(bag);

	assertTrue(sub.getLookup().containsKey(c1.name));
	assertTrue(sub.getLookup().containsKey(c2.name));
	assertTrue(sub.getLookup().containsKey(c3.name));
	assertTrue(sub.getLookup().containsKey(c4.name));
    }

    @Test
    public void testRemovedEntitiesNotInLookup() {
	IntBag bag = new IntBag();
	bag.add(id1);
	bag.add(id2);
	bag.add(id3);
	bag.add(id4);

	sub.inserted(bag);
	bag.removeValue(id1);
	bag.removeValue(id2);
	sub.removed(bag);

	assertTrue(sub.getLookup().containsKey(c1.name));
	assertTrue(sub.getLookup().containsKey(c2.name));
	assertFalse(sub.getLookup().containsKey(c3.name));
	assertFalse(sub.getLookup().containsKey(c4.name));	
    }

    @Test
    public void testEntitiesRemovedSequentiallyNotInLookup() {
	IntBag bag = new IntBag();
	bag.add(id1);
	bag.add(id2);
	bag.add(id3);
	sub.inserted(bag);
	bag.removeValue(id1);
	bag.removeValue(id2);
	sub.removed(bag);
	IntBag removeBag = new IntBag();
	removeBag.add(id2);
	sub.removed(removeBag);

	assertTrue(sub.getLookup().containsKey(c1.name));
	assertFalse(sub.getLookup().containsKey(c2.name));
	assertFalse(sub.getLookup().containsKey(c3.name));
    }
    
}
