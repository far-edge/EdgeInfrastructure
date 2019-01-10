package eu.faredge.edgeInfrastructure.registry.models.full;

import java.util.ArrayList;
import java.util.List;

import eu.faredge.edgeInfrastructure.registry.models.dk.DK;

public class FullDataKindReferenceIDs
{
	private List<DK> dataKindReferenceID;

	
	public List<DK> getDataKindReferenceID()
	{
		if( dataKindReferenceID==null)
		{
			dataKindReferenceID = new ArrayList<DK>();
		}
		return dataKindReferenceID;
	}

	public void setDataKindReferenceID(List<DK> dataKindReferenceID)
	{
		this.dataKindReferenceID = dataKindReferenceID;
	}

}
