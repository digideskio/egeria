/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.userinterface.accessservices.service;

import org.odpi.openmetadata.accessservices.assetcatalog.client.AssetCatalog;
import org.odpi.openmetadata.accessservices.assetcatalog.exception.InvalidParameterException;
import org.odpi.openmetadata.accessservices.assetcatalog.exception.PropertyServerException;
import org.odpi.openmetadata.accessservices.assetcatalog.model.AssetDescription;
import org.odpi.openmetadata.accessservices.assetcatalog.model.Classification;
import org.odpi.openmetadata.accessservices.assetcatalog.model.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Asset Catalog OMAS Service provides an interface to search for assets using the Asset Catalog OMAS client
 */
@Service
public class AssetCatalogOMASService {
    private final AssetCatalog assetCatalog;
    private static final Logger LOG = LoggerFactory.getLogger(AssetCatalogOMASService.class);
    //TODO add authentication
    private final String user = "demo";

    @Autowired
    public AssetCatalogOMASService(AssetCatalog assetCatalog) {
        this.assetCatalog = assetCatalog;
    }

    /**
     * Fetch asset's header, classification and properties
     *
     * @param searchCriteria the searchCriteria
     * @return the assets for the search criteria
     * @throws PropertyServerException   there is a problem retrieving information from the property server
     * @throws InvalidParameterException one of the parameters is null or invalid
     */
    public List<AssetDescription> searchAssets(String searchCriteria) throws PropertyServerException, InvalidParameterException {
        try {
            return assetCatalog.searchAssets(user, searchCriteria).getAssetDescriptionList();
        } catch (InvalidParameterException | PropertyServerException e) {
            LOG.error(String.format("Error retrieving asset details for '%s'", searchCriteria));
            throw e;
        }
    }

    /**
     * Fetch asset's header and classification
     *
     * @param assetId the unique identifier for the asset
     * @return the asset with its header and the list of associated classifications
     * @throws PropertyServerException   there is a problem retrieving information from the property server
     * @throws InvalidParameterException one of the parameters is null or invalid
     */
    public List<AssetDescription> getAssetSummary(String assetId) throws PropertyServerException, InvalidParameterException {
        try {
            return assetCatalog.getAssetSummary(user, assetId).getAssetDescriptionList();
        } catch (InvalidParameterException | PropertyServerException e) {
            LOG.error(String.format("Error retrieving asset description for %s", assetId));
            throw e;
        }
    }

    /**
     * Fetch asset's header, classification and properties
     *
     * @param assetId the unique identifier for the asset
     * @return the asset with its header and the list of associated classifications and specific properties
     * @throws PropertyServerException   there is a problem retrieving information from the property server
     * @throws InvalidParameterException one of the parameters is null or invalid
     */
    public List<AssetDescription> getAssetDetails(String assetId) throws PropertyServerException, InvalidParameterException {
        try {
            return assetCatalog.getAssetDetails(user, assetId).getAssetDescriptionList();
        } catch (InvalidParameterException | PropertyServerException e) {
            LOG.error(String.format("Error retrieving asset details for %s", assetId));
            throw e;
        }
    }



    /**
     * Fetch asset's header, classification, properties and relationships
     *
     * @param assetId the unique identifier for the asset
     * @return the asset with its header and the list of associated classifications
     * @throws PropertyServerException   there is a problem retrieving information from the property server
     * @throws InvalidParameterException one of the parameters is null or invalid
     */
    public List<AssetDescription> getAssetUniverse(String assetId) throws PropertyServerException, InvalidParameterException {
        try {
            return assetCatalog.getAssetUniverse(user, assetId).getAssetDescriptionList();
        } catch (InvalidParameterException | PropertyServerException e) {
            LOG.error(String.format("Error retrieving asset universe for %s", assetId));
            throw e;
        }
    }

    /**
     * Fetch the relationships for a specific asset
     *
     * @param assetId the unique identifier for the asset
     * @return list of relationships for the given asset
     * @throws PropertyServerException   there is a problem retrieving information from the property server
     * @throws InvalidParameterException one of the parameters is null or invalid
     */
    public List<Relationship> getAssetRelationships(String assetId) throws PropertyServerException, InvalidParameterException {
        try {
            return assetCatalog.getAssetRelationships(user, assetId).getRelationships();
        } catch (InvalidParameterException | PropertyServerException e) {
            LOG.error(String.format("Error retrieving asset relationships for %s", assetId));
            throw e;
        }
    }

    /**
     * Fetch the relationships for a specific asset and relationship type
     *
     * @param assetId          the unique identifier for the asset
     * @param relationshipType the relationship type
     * @return list of relationships for the given asset and relationship type
     * @throws PropertyServerException   there is a problem retrieving information from the property server
     * @throws InvalidParameterException one of the parameters is null or invalid
     */
    public List<Relationship> getAssetRelationshipsForType(String assetId,
                                                           String relationshipType) throws PropertyServerException, InvalidParameterException {
        try {
            return assetCatalog.getAssetRelationshipsForType(user, assetId, relationshipType).getRelationships();
        } catch (InvalidParameterException | PropertyServerException e) {
            LOG.error(String.format("Error retrieving asset relationships for %s and relationship type %s", assetId,
                    relationshipType));
            throw e;
        }
    }

    /**
     * Fetch the classification for a specific asset
     *
     * @param assetId the unique identifier for the asset
     * @return the classification for the asset
     * @throws PropertyServerException   there is a problem retrieving information from the property server
     * @throws InvalidParameterException one of the parameters is null or invalid
     */
    public List<Classification> getClassificationForAsset(String assetId) throws PropertyServerException, InvalidParameterException {
        try {
            return assetCatalog.getClassificationForAsset(user, assetId).getClassifications();
        } catch (InvalidParameterException | PropertyServerException e) {
            LOG.error(String.format("Error retrieving asset classifications for %s", assetId));
            throw e;
        }
    }
}