package domainapp.dom.modules.petclinic;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.DatastoreIdentity;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;

import com.google.common.collect.ComparisonChain;

import org.joda.time.DateTime;

import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Title;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE,
        schema = "petclinic",
        table = "Visit"
)
@DatastoreIdentity(strategy = IdGeneratorStrategy.IDENTITY, column = "id")
@Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
public class Visit implements Comparable<Visit> {


    //region > pet (Property)
    private Pet pet;

    @Column(name = "petId", allowsNull = "true")
    @MemberOrder(sequence = "1")
    @Title(sequence = "1")
    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
    //endregion

    //region > checkInTime (Property)
    private DateTime checkInTime;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "false")
    @Persistent
    @Title(sequence = "2", prepend = "-")
    public DateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(DateTime checkInTime) {
        this.checkInTime = checkInTime;
    }
    //endregion

    //region > checkOutTime (Property)
    private DateTime checkOutTime;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "true")
    @Persistent
    @Title(sequence = "3", prepend = "-")
    public DateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(DateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
    //endregion

    //region > notes (Property)
    private String notes;

    @MemberOrder(sequence = "4")
    @Column(allowsNull = "true")
    @PropertyLayout(multiLine = 5)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    //endregion

    @Override
    public int compareTo(Visit o) {
        return ComparisonChain.start()
                .compare(getPet(), o.getPet())
                .compare(getCheckInTime(), o.getCheckInTime())
                .result();
    }

}
