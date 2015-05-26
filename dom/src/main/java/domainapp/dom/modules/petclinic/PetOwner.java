package domainapp.dom.modules.petclinic;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Unique;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.base.Predicate;
import com.google.common.collect.ComparisonChain;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.CollectionLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.RenderType;
import org.apache.isis.applib.annotation.Title;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "petclinic",
        table = "PetOwner"
)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@Unique(name = "Owner_firstName_lastName_UNQ", members = { "firstName", "lastName" })
public class PetOwner implements Comparable<PetOwner> {

    //region > First Name (property)
    private String firstName;

    @Column(allowsNull = "false")
    @Title(sequence = "2")
    @MemberOrder(sequence = "1")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
    //endregion

    //region > Last Name (property)
    private String lastName;

    @Column(allowsNull = "false")
    @Title(sequence = "1", append = ", ")
    @MemberOrder(sequence = "2")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    //endregion

    //region > pets (collection)
    @Persistent(mappedBy = "petOwner", dependentElement = "false")
    private SortedSet<Pet> pets = new TreeSet<Pet>();

    @MemberOrder(sequence = "3")
    @CollectionLayout(render = RenderType.EAGERLY)
    public SortedSet<Pet> getPets() {
        return pets;
    }

    public void setPets(final SortedSet<Pet> pets) {
        this.pets = pets;
    }
    //endregion

    //region > compareTo
    @Override
    public int compareTo(PetOwner other) {
        return ComparisonChain.start()
                .compare(this.getLastName(), other.getLastName())
                .compare(this.getFirstName(), other.getFirstName())
                .result();
    }
    //endregion

    //region > Injected
    @javax.inject.Inject
    @SuppressWarnings("unused")
    private DomainObjectContainer container;
    //endregion

    //region > predicates
    public static class Predicates {

        public static Predicate<PetOwner> contains(final String string) {
            return new Predicate<PetOwner>() {
                @Override
                public boolean apply(final PetOwner petOwner) {
                    return petOwner.getFirstName().toLowerCase().contains(string.toLowerCase()) ||
                            petOwner.getLastName().toLowerCase().contains(string.toLowerCase());
                }
            };
        }
    }
    //endregion
}
