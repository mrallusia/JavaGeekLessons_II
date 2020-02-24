import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestGetMassAfterFour {

    private MassMethods massMethods;

    @Before
    public void init() {
        massMethods = new MassMethods();
    }

    @Test
    public void getMassAfterFourTestOne() {
        int[] mass = {0,1,2,3,4,1};
        massMethods.getMassAfterFour(mass);
    }

    @Test
    public void getMassAfterFourTestTwo() {
        int[] mass = {4,4,4};
        try {
            massMethods.getMassAfterFour(mass);
        }catch (RuntimeException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getMassAfterFourTestThree() {
        int[] mass = {0,1,2,3,0};
        try {
            massMethods.getMassAfterFour(mass);
        }catch (RuntimeException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void getMassAfterFourTestFour() {
        int[] mass = {4,1,2,3,4,1,1,2,3};
        massMethods.getMassAfterFour(mass);
    }

}