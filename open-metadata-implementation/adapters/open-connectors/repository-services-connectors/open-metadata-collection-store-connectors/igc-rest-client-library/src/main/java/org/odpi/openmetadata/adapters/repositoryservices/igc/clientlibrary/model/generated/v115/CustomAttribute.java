/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.adapters.repositoryservices.igc.clientlibrary.model.generated.v115;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.odpi.openmetadata.adapters.repositoryservices.igc.clientlibrary.model.common.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.ArrayList;

/**
 * POJO for the 'custom_attribute' asset type in IGC, displayed as 'Custom Attribute' in the IGC UI.
 * <br><br>
 * (this code has been generated based on out-of-the-box IGC metadata types;
 *  if modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomAttribute extends Reference {

    public static String getIgcTypeId() { return "custom_attribute"; }

    /**
     * The 'custom_attribute_type' property, displayed as 'Custom Attribute Type' in the IGC UI.
     * <br><br>
     * Can be one of the following values:
     * <ul>
     *     <li>BOOLEAN (displayed in the UI as 'Boolean')</li>
     *     <li>INTEGER (displayed in the UI as 'INTEGER')</li>
     *     <li>DOUBLE (displayed in the UI as 'Number')</li>
     *     <li>STRING (displayed in the UI as 'Predefined Values')</li>
     *     <li>DATE (displayed in the UI as 'Date')</li>
     *     <li>TEXT (displayed in the UI as 'Text')</li>
     *     <li>REFERENCE (displayed in the UI as 'Relationship')</li>
     * </ul>
     */
    protected String custom_attribute_type;

    /**
     * The 'applies_to' property, displayed as 'Applies To' in the IGC UI.
     */
    protected String applies_to;

    /**
     * The 'name' property, displayed as 'Name' in the IGC UI.
     */
    protected String name;

    /**
     * The 'description' property, displayed as 'Description' in the IGC UI.
     */
    protected String description;


    /** @see #custom_attribute_type */ @JsonProperty("custom_attribute_type")  public String getCustomAttributeType() { return this.custom_attribute_type; }
    /** @see #custom_attribute_type */ @JsonProperty("custom_attribute_type")  public void setCustomAttributeType(String custom_attribute_type) { this.custom_attribute_type = custom_attribute_type; }

    /** @see #applies_to */ @JsonProperty("applies_to")  public String getAppliesTo() { return this.applies_to; }
    /** @see #applies_to */ @JsonProperty("applies_to")  public void setAppliesTo(String applies_to) { this.applies_to = applies_to; }

    /** @see #name */ @JsonProperty("name")  public String getTheName() { return this.name; }
    /** @see #name */ @JsonProperty("name")  public void setTheName(String name) { this.name = name; }

    /** @see #description */ @JsonProperty("description")  public String getDescription() { return this.description; }
    /** @see #description */ @JsonProperty("description")  public void setDescription(String description) { this.description = description; }

    public static final Boolean isCustomAttribute(Object obj) { return (obj.getClass() == CustomAttribute.class); }

}
