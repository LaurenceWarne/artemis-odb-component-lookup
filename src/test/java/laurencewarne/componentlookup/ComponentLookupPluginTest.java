package laurencewarne.componentlookup;

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
	setup.with(new SystemA(), new SystemB());
    }

    @Test
    public void testCanInitWorldWithPlugin() {
	world = new World(setup.build());
    }

    @Test
    public void testCanInitWorldWithPluginAndSystemWithPrimitiveLookup() {
	setup.with(new SystemC());
	world = new World(setup.build());
    }

}
