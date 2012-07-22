package db;

import javax.persistence.Entity;

import org.ubdynamics.data.jpa.Model;

@Entity
public class ContactInfo extends Model {

	public String type;
	public String value;

}
