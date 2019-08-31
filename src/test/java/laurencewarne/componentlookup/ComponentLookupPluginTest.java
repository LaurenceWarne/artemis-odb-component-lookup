package laurencewarne.componentlookup;

import static org.junit.Assert.assertFalse;

import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;

import org.junit.Before;
import org.junit.Test;

public class ComponentLookupPluginTest {

    private WorldConfigurationBuilder setup;
    private World world;

    @Before
    public void setUp() {
	setup = new WorldConfigurationBuilder();
	setup.with(new ComponentLookupPlugin());
    }

    @Test
    public void testCanInitWorldWithPlugin() {
	setup.with(new SystemA(), new SystemB());
	world = new World(setup.build());
    }

    @Test
    public void testCanInitWorldWithPluginAndSystemWithPrimitiveLookup() {
	setup.with(new SystemA(), new SystemB());
	setup.with(new SystemC());
	world = new World(setup.build());
    }

    @Test
    public void testCanTrackEntitiesMadeInSystemInitBlock() {
	SystemInit sys = new SystemInit();
	setup.with(sys);
	
	world = new World(setup.build());
	world.process();
	assertFalse(sys.lookup.isEmpty());
    }

}
