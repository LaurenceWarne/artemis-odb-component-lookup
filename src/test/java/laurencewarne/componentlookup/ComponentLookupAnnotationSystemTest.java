package laurencewarne.componentlookup;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.utils.ObjectIntMap;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ComponentLookupAnnotationSystemTest {

    private ComponentLookupAnnotationSystem sys;
    private ComponentLookupCreationSystem lookupSys;
    private WorldConfigurationBuilder setup;
    private World world;
    private int id1, id2;
    
    @Before
    public void setUp() throws NoSuchFieldException, SecurityException {
	setup = new WorldConfigurationBuilder();
	setup.with(
	    lookupSys = Mockito.mock(ComponentLookupCreationSystem.class),
	    sys = new ComponentLookupAnnotationSystem()
	);
	when(lookupSys.createLookup(anyString(), any(), any()))
	    .thenReturn(new ObjectIntMap<>());
    }

    private void createWorld() {
	world = new World(setup.build());
	id1 = world.create();
	id2 = world.create();
    }

    @Test
    public void testAnnotationSystemWithNoLookups() {
	createWorld();
    }

    @Test
    public void testAnnotationSystemInjectsLookupIntoPublicField() {
	SystemA testSys = new SystemA();
	setup.with(testSys);
	createWorld();
	assertEquals(new ObjectIntMap<String>(), testSys.lookup);
    }

    @Test
    public void testAnnotationSystemInjectsLookupIntoPrivateField() {
	SystemB testSys = new SystemB();
	setup.with(testSys);
	createWorld();
	assertEquals(new ObjectIntMap<String>(), testSys.getLookup());
    }
    
}
