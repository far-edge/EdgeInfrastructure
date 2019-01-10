package eu.sensap.faredge.dataRoutingClient.senml;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/***
 * This embedded class carries the SenML data model and the annotation for JSON representation 
 *  	
 */	
@JsonInclude(Include.NON_NULL)
public class SenmlDataObject {
	
		private String baseName;		// bn
		private Number baseTime;		// bt
		private String baseUnit;		// bu
		private Number baseValue;		// bv
		private Number baseSum;			// bs
		private Number version;			// bver
		private String name;			// n
		private String unit;			// u
		private Number value;			// v
		private String stringValue;		// vs
		private Boolean booleanValue;	// vb
		private String dataValue;		// vd
		private Number valueSum;		// s
		private Number time;			// t
		private Number updateTime;		// ut
		private String link;			// l

		// SenML record constructors
		
		public SenmlDataObject()
		{

		}

		public SenmlDataObject(String name, String unit, Number Value)
		{
			setName(name);
			setUnit(unit);
			setValue(Value);
		}

		public SenmlDataObject(String baseName, Number baseTime, Number version)
		{
			setBaseName(baseName);
			setBaseTime(baseTime);
			setVersion(version);
		}
		
		//Getters and Setters

		@JsonGetter("n")
		public String getName()
		{
			return name;
		}

		@JsonSetter("n")
		public void setName(String n)
		{
			this.name = n;
		}

		@JsonGetter("u")
		public String getUnit()
		{
			return unit;
		}

		@JsonSetter("u")
		public void setUnit(String u)
		{
			this.unit = u;
		}

		@JsonGetter("v")
		public Number getValue()
		{
			return value;
		}

		@JsonSetter("v")
		public void setValue(Number v)
		{
			this.value = v;
		}

		@JsonGetter("vs")
		public String getStringValue()
		{
			return stringValue;
		}

		@JsonSetter("vs")
		public void setStringValue(String vs)
		{
			this.stringValue = vs;
		}

		@JsonGetter("vb")
		public Boolean getBooleanValue()
		{
			return booleanValue;
		}

		@JsonSetter("vb")
		public void setBooleanValue(Boolean vb)
		{
			this.booleanValue = vb;
		}

		@JsonGetter("vd")
		public String getDataValue()
		{
			return dataValue;
		}

		@JsonSetter("vd")
		public void setDataValue(String vd)
		{
			this.dataValue = vd;
		}

		@JsonGetter("s")
		public Number getValueSum()
		{
			return valueSum;
		}

		@JsonSetter("s")
		public void setValueSum(Number s)
		{
			this.valueSum = s;
		}

		@JsonGetter("t")
		public Number getTime()
		{
			return time;
		}

		@JsonSetter("t")
		public void setTime(Number t)
		{
			this.time = t;
		}

		@JsonGetter("ut")
		public Number getUpdateTime()
		{
			return updateTime;
		}

		@JsonSetter("ut")
		public void setUpdateTime(Number ut)
		{
			this.updateTime = ut;
		}

		@JsonGetter("l")
		public String getLink()
		{
			return link;
		}

		@JsonSetter("l")
		public void setLink(String l)
		{
			this.link = l;
		}

		@JsonGetter("bn")
		public String getBaseName()
		{
			return baseName;
		}

		@JsonSetter("bn")
		public void setBaseName(String bn)
		{
			this.baseName = bn;
		}

		@JsonGetter("bt")
		public Number getBaseTime()
		{
			return baseTime;
		}

		@JsonSetter("bt")
		public void setBaseTime(Number bt)
		{
			this.baseTime = bt;
		}

		@JsonGetter("bu")
		public String getBaseUnit()
		{
			return baseUnit;
		}

		@JsonSetter("bu")
		public void setBaseUnit(String bu)
		{
			this.baseUnit = bu;
		}

		@JsonGetter("bv")
		public Number getBaseValue()
		{
			return baseValue;
		}

		@JsonSetter("bv")
		public void setBaseValue(Number bv)
		{
			this.baseValue = bv;
		}

		@JsonGetter("bs")
		public Number getBaseSum()
		{
			return baseSum;
		}

		@JsonSetter("bs")
		public void setBaseSum(Number bs)
		{
			this.baseSum = bs;
		}

		@JsonGetter("bver")
		public Number getVersion()
		{
			return version;
		}

		@JsonSetter("bver")
		public void setVersion(Number bver)
		{
			this.version = bver;
		}

}
