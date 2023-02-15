package advanced;

import com.sandwich.koan.Koan;

import static com.sandwich.util.Assert.fail;

public class AboutMocks {

    static interface Collaborator {
        public void doBusinessStuff();
    }

    static class ExplosiveCollaborator implements Collaborator {
        public void doBusinessStuff() {
            fail("Default collaborator's behavior is complicating testing.");
        }
    }

    static class ClassUnderTest {
        Collaborator c;

        public ClassUnderTest() {
            // default is to pass a broken Collaborator, test should pass one
            // that doesn't throw exception
            this(new ExplosiveCollaborator());
        }

        public ClassUnderTest(Collaborator c) {
            this.c = c;
        }

        public boolean doSomething() {
            c.doBusinessStuff();
            return true;
        }
    }

    /**
     * A non-explosive version of the Collaborator interface!
     */
    static class CalmCollaborator implements Collaborator
    {
        public void doBusinessStuff()
        {

        }
    }

    @Koan
    public void simpleAnonymousMock() {
        // HINT: pass a safe Collaborator implementation to constructor
        // new ClassUnderTest(new Collaborator(){... it should not be the
        // objective of this test to test that collaborator, so replace it

        Collaborator calmCollaborator = new CalmCollaborator();
        new ClassUnderTest( calmCollaborator).doSomething();
        //new ClassUnderTest().doSomething();
    }

}
