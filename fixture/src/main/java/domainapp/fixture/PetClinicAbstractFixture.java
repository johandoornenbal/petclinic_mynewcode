package domainapp.fixture;

import org.apache.isis.applib.fixturescripts.FixtureScript;

/**
 * Created by jodo on 26/05/15.
 */
public abstract class PetClinicAbstractFixture extends FixtureScript {


        public void deleteFrom(final Class<?> cls) {
            isisJdoSupport.executeUpdate(String.format("delete from petclinic.\"%s\"", cls.getSimpleName()));
        }

        @javax.inject.Inject
        private org.apache.isis.applib.services.jdosupport.IsisJdoSupport isisJdoSupport;

}
