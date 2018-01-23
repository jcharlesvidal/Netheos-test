package faq.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author jcvidal
 *
 *
 * Model for Faq table (with monodirectionnal ManyToMany to Tag)
 */
@Entity
@Table(name="faq")
public class Faq {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	private String question;
	
	@NotNull
	private String reponse;
	
	@ManyToMany(fetch = FetchType.EAGER,cascade = { CascadeType.MERGE })
    @JoinTable(
        name = "faq_tag", 
        joinColumns = { @JoinColumn(name = "faq_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    private Set<Tag> tags = new HashSet<Tag>();	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getReponse() {
		return reponse;
	}
	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
	public Set<Tag> getTags() {
		return tags;
	}
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	public String toString() {
		String ret = "q = "+question+" r = "+reponse;
		if (tags != null) {
			ret += " [";
			for (Tag tag:tags) {
				ret += tag.getValue()+" ";
			}
			ret +="]";
		}
		return ret;
	}
}
