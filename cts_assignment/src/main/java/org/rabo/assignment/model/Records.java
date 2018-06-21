package org.rabo.assignment.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Records")
public class Records implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<RaboModel> record;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((record == null) ? 0 : record.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Records other = (Records) obj;
		if (record == null) {
			if (other.record != null)
				return false;
		} else if (!record.equals(other.record))
			return false;
		return true;
	}

	public List<RaboModel> getRecord() {
		return record;
	}

	public void setRecord(List<RaboModel> record) {
		this.record = record;
	}
	
	
	
	
	
}
