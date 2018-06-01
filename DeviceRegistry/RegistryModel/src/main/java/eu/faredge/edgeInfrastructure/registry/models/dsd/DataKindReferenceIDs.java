package eu.faredge.edgeInfrastructure.registry.models.dsd;

import java.util.ArrayList;
import java.util.List;

public class DataKindReferenceIDs
{
	private List<String> dataKindReferenceID;

	
	public List<String> getDataKindReferenceID()
	{
		if( dataKindReferenceID==null)
		{
			dataKindReferenceID = new ArrayList<String>();
		}
		return dataKindReferenceID;
	}

	public void setDataKindReferenceID(List<String> dataKindReferenceID)
	{
		this.dataKindReferenceID = dataKindReferenceID;
	}

}
