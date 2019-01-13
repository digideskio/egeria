/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.adapters.repositoryservices.igc.clientlibrary.model.generated.v11502sp3;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.odpi.openmetadata.adapters.repositoryservices.igc.clientlibrary.model.common.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * POJO for the 'user_role' asset type in IGC, displayed as 'User Role' in the IGC UI.
 * <br><br>
 * (this code has been generated based on out-of-the-box IGC metadata types;
 *  if modifications are needed, eg. to handle custom attributes,
 *  extending from this class in your own custom class is the best approach.)
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserRole extends Reference {

    public static String getIgcTypeId() { return "user_role"; }
    public static String getIgcTypeDisplayName() { return "User Role"; }

    /**
     * The 'name' property, displayed as 'Role' in the IGC UI.
     */
    protected String name;

    /**
     * The 'system_role' property, displayed as 'System Role' in the IGC UI.
     */
    protected Boolean system_role;

    /**
     * The 'users' property, displayed as 'Users' in the IGC UI.
     * <br><br>
     * Will be a {@link ReferenceList} of {@link Steward} objects.
     */
    protected ReferenceList users;


    /** @see #name */ @JsonProperty("name")  public String getTheName() { return this.name; }
    /** @see #name */ @JsonProperty("name")  public void setTheName(String name) { this.name = name; }

    /** @see #system_role */ @JsonProperty("system_role")  public Boolean getSystemRole() { return this.system_role; }
    /** @see #system_role */ @JsonProperty("system_role")  public void setSystemRole(Boolean system_role) { this.system_role = system_role; }

    /** @see #users */ @JsonProperty("users")  public ReferenceList getUsers() { return this.users; }
    /** @see #users */ @JsonProperty("users")  public void setUsers(ReferenceList users) { this.users = users; }

    public static Boolean canBeCreated() { return false; }
    public static Boolean includesModificationDetails() { return false; }
    private static final List<String> NON_RELATIONAL_PROPERTIES = Arrays.asList(
        "name",
        "system_role"
    );
    private static final List<String> PAGED_RELATIONAL_PROPERTIES = Arrays.asList(
        "users"
    );
    private static final List<String> ALL_PROPERTIES = Arrays.asList(
        "name",
        "system_role",
        "users"
    );
    public static List<String> getNonRelationshipProperties() { return NON_RELATIONAL_PROPERTIES; }
    public static List<String> getPagedRelationshipProperties() { return PAGED_RELATIONAL_PROPERTIES; }
    public static List<String> getAllProperties() { return ALL_PROPERTIES; }
    public static Boolean isUserRole(Object obj) { return (obj.getClass() == UserRole.class); }

}
