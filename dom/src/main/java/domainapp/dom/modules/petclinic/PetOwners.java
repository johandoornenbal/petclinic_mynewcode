package domainapp.dom.modules.petclinic;

import java.util.List;

import javax.inject.Inject;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ParameterLayout;

@DomainService
public class PetOwners {

    public String getId() {
        return "owner";
    }

    public String iconName() {
        return "PetOwner";
    }

    public List<PetOwner> allOwners() {
        return container.allInstances(PetOwner.class);
    }

    public List<PetOwner> findByName(String name) {
        return container.allMatches(PetOwner.class, PetOwner.Predicates.contains(name));
    }

    @MemberOrder(sequence = "2")
    public PetOwner addOwner(
            final @ParameterLayout(named = "First Name") String firstName,
            final @ParameterLayout(named = "Last Name") String lastName) {
        final PetOwner obj = container.newTransientInstance(PetOwner.class);
        obj.setFirstName(firstName);
        obj.setLastName(lastName);
        container.persistIfNotAlready(obj);
        return obj;
    }

    @Inject
    DomainObjectContainer container;

}
