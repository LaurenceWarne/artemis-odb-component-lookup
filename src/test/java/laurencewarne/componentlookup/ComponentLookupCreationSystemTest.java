package laurencewarne.componentlookup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.utils.ObjectIntMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComponentLookupCreationSystemTest {

    private ComponentLookupCreationSystem sys;
    private World world;
    private int id1, id2;
    
    @Before
    public void setUp() {
	WorldConfigurationBuilder setup = new WorldConfigurationBuilder();
	setup.with(sys = new ComponentLookupCreationSystem());
	world = new World(setup.build());
	id1 = world.create();
	id2 = world.create();
    }

    @Test
    public void testCanCreateLookupForPublicComponentField() {
	ObjectIntMap<String> map;
	try {
	    map = sys.createLookup("name", ComponentA.class, String.class);
	} catch (NoSuchFieldException | SecurityException e) {
	    Assert.fail();
	}
    }

    @Test
    public void testCanCreateLookupForPrivateComponentField() {
	ObjectIntMap<String> map;
	try {
	    map = sys.createLookup("birthday", ComponentA.class, String.class);
	} catch (NoSuchFieldException | SecurityException e) {
	    Assert.fail();
	}
    }    

    @Test
    public void testLookupTracksNewlyAddedComponents() {
	ObjectIntMap<String> map = null;
	try {
	    map = sys.createLookup("name", ComponentA.class, String.class);
	} catch (NoSuchFieldException | SecurityException e) {
	    Assert.fail();
	}
	ComponentA c1 = world.getMapper(ComponentA.class).create(id1);
	c1.name = "Dave";
	ComponentA c2 = world.getMapper(ComponentA.class).create(id2);
	c1.name = "Steve";
	world.process();

	assertTrue(map.containsKey(c1.name));
	assertTrue(map.containsKey(c2.name));
	assertEquals(id1, map.get(c1.name, -1));
	assertEquals(id2, map.get(c2.name, -1));
    }

    @Test
    public void testLookupTracksRemovedComponents() {
	ObjectIntMap<String> map = null;
	try {
	    map = sys.createLookup("name", ComponentA.class, String.class);
	} catch (NoSuchFieldException | SecurityException e) {
	    Assert.fail();
	}
	ComponentA c1 = world.getMapper(ComponentA.class).create(id1);
	c1.name = "Dave";
	ComponentA c2 = world.getMapper(ComponentA.class).create(id2);
	c1.name = "Steve";
	world.process();
	world.delete(id1);
	world.process();	

	assertFalse(map.containsKey(c1.name));
	assertTrue(map.containsKey(c2.name));
	assertEquals(id2, map.get(c2.name, -1));
    }

    @Test
    public void testLookupReturnsSameInstanceOnSameArguments() {
	ObjectIntMap<String> mapA = null;
	try {
	    mapA = sys.createLookup("name", ComponentA.class, String.class);
	} catch (NoSuchFieldException | SecurityException e) {
	    Assert.fail();
	}
	ObjectIntMap<String> mapB = null;
	try {
	    mapB = sys.createLookup("name", ComponentA.class, String.class);
	} catch (NoSuchFieldException | SecurityException e) {
	    Assert.fail();
	}
	assertTrue(mapA == mapB);
    }

    @Test
    public void testLookupReturnsDifferentInstanceOnDifferentArguments() {
	ObjectIntMap<String> mapA = null;
	try {
	    mapA = sys.createLookup("name", ComponentA.class, String.class);
	} catch (NoSuchFieldException | SecurityException e) {
	    Assert.fail();
	}
	ObjectIntMap<String> mapB = null;
	try {
	    mapB = sys.createLookup("birthday", ComponentA.class, String.class);
	} catch (NoSuchFieldException | SecurityException e) {
	    Assert.fail();
	}
	assertFalse(mapA == mapB);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testExceptionThrownOnFieldAndFieldClassTypeMismatch() {
	ObjectIntMap<Integer> mapA = null;
	try {
	    mapA = sys.createLookup("name", ComponentA.class, Integer.class);
	} catch (NoSuchFieldException | SecurityException e) {
	    Assert.fail();
	}
    }

    @Test
    public void testNoSuchFieldExceptionThrownOnInvalidField() {
	ObjectIntMap<Integer> mapA = null;
	try {
	    mapA = sys.createLookup("notAField", ComponentA.class, Integer.class);
	} catch (SecurityException e) {
	    Assert.fail();
	} catch (NoSuchFieldException e) {
	    return;
	}
	Assert.fail();
    }
}
