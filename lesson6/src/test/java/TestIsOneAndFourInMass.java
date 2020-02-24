import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestIsOneAndFourInMass {
    private MassMethods massMethods;

    @Before
    public void init() {
        massMethods = new MassMethods();
    }

    @Test
    public void isOneAndFourInMassTestOne() {
        int[] mass = {1,1,1,4,4,1,4,4};
        Assert.assertTrue(massMethods.isOneAndFourInMass(mass));
    }

    @Test
    public void isOneAndFourInMassTestTwo() {
        int[] mass = {1,1,1,1};
        Assert.assertFalse(massMethods.isOneAndFourInMass(mass));
    }

    @Test
    public void isOneAndFourInMassTestThree() {
        int[] mass = {4,4};
        Assert.assertFalse(massMethods.isOneAndFourInMass(mass));
    }

    @Test
    public void isOneAndFourInMassTestFour() {
        int[] mass = {1,3,1,4,4};
        Assert.assertFalse(massMethods.isOneAndFourInMass(mass));
    }
}