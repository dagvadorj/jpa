package db;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.ubdynamics.data.jpa.Model;

@Entity
public class Employee extends Model {

	public String name;
	public Integer age;
	
	@OneToMany
	public List<ContactInfo> contactInfos;

}
