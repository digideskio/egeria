/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.accessservices.assetcatalog.service;


import org.odpi.openmetadata.accessservices.assetcatalog.exception.AssetCatalogErrorCode;
import org.odpi.openmetadata.accessservices.assetcatalog.exception.PropertyServerException;
import org.odpi.openmetadata.accessservices.assetcatalog.exception.RelationshipNotFoundException;
import org.odpi.openmetadata.accessservices.assetcatalog.model.SequenceOrderType;
import org.odpi.openmetadata.accessservices.assetcatalog.model.Status;
import org.odpi.openmetadata.accessservices.assetcatalog.model.rest.body.SearchParameters;
import org.odpi.openmetadata.accessservices.assetcatalog.model.rest.responses.RelationshipResponse;
import org.odpi.openmetadata.accessservices.assetcatalog.model.rest.responses.RelationshipsResponse;
import org.odpi.openmetadata.accessservices.assetcatalog.util.Converter;
import org.odpi.openmetadata.accessservices.assetcatalog.util.ExceptionHandler;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.OMRSMetadataCollection;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.MatchCriteria;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.SequencingOrder;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceProperties;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.InstanceStatus;
import org.odpi.openmetadata.repositoryservices.connectors.stores.metadatacollectionstore.properties.instances.Relationship;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.FunctionNotSupportedException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.InvalidParameterException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.PagingErrorException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.PropertyErrorException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RelationshipNotKnownException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.RepositoryErrorException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.TypeErrorException;
import org.odpi.openmetadata.repositoryservices.ffdc.exception.UserNotAuthorizedException;

import java.util.List;

/**
 * The AssetCatalogRelationshipService provides the server-side implementation of the Asset Catalog Open Metadata
 * Assess Service (OMAS).
 * This service provide the functionality to fetch asset relationships and details about specific relationships.
 */
public class AssetCatalogRelationshipService {

    static AssetCatalogInstanceHandler assetCatalogInstanceHandler = new AssetCatalogInstanceHandler();

    private Converter converter = new Converter();
    private ExceptionHandler exceptionHandler = new ExceptionHandler();

    public RelationshipResponse getRelationshipById(String serverName, String userId, String relationshipId) {

        RelationshipResponse response = new RelationshipResponse();

        try {
            Relationship relationship = getRelationship(serverName, userId, relationshipId);
            response.setRelationship(converter.toRelationship(relationship));
        } catch (PropertyServerException | RelationshipNotFoundException e) {
            exceptionHandler.captureAssetCatalogExeption(response, e);
        } catch (InvalidParameterException | RepositoryErrorException | RelationshipNotKnownException | UserNotAuthorizedException e) {
            exceptionHandler.captureOMRSCheckedExceptionBase(response, e);
        }

        return response;
    }


    public RelationshipsResponse getRelationshipByProperty(String serverName, String userId, String matchProperty,
                                                           SearchParameters searchParameters) {
        RelationshipsResponse response = new RelationshipsResponse();
        try {

            String relationshipTypeGUID = null;
            String propertyValue = null;
            Integer pageSize = 0;
            Integer fromElement = 0;
            SequenceOrderType orderType = null;
            String orderProperty = null;
            Status status = null;

            if (searchParameters != null) {
                fromElement = searchParameters.getOffset();
                pageSize = searchParameters.getLimit();
                orderType = searchParameters.getOrderType();
                orderProperty = searchParameters.getOrderProperty();
                propertyValue = searchParameters.getPropertyValue();
                status = searchParameters.getStatus();
                if (searchParameters.getTypes() != null && !searchParameters.getTypes().isEmpty()) {
                    relationshipTypeGUID = searchParameters.getTypes().get(0);
                }
            }

            List<Relationship> relationshipsByProperty = getRelationshipsByProperty(serverName, userId,
                    relationshipTypeGUID,
                    matchProperty,
                    propertyValue,
                    pageSize,
                    fromElement,
                    orderType,
                    orderProperty,
                    status);

            response.setRelationships(converter.toRelationships(relationshipsByProperty));
        } catch (PagingErrorException
                | FunctionNotSupportedException
                | UserNotAuthorizedException
                | TypeErrorException
                | PropertyErrorException
                | RepositoryErrorException
                | InvalidParameterException e) {
            exceptionHandler.captureOMRSCheckedExceptionBase(response, e);
        } catch (PropertyServerException | RelationshipNotFoundException e) {
            exceptionHandler.captureAssetCatalogExeption(response, e);
        }
        return response;
    }


    public RelationshipsResponse searchForRelationships(String serverName, String userId, String relationshipTypeGUID,
                                                        String searchCriteria, SearchParameters searchParameters) {
        RelationshipsResponse response = new RelationshipsResponse();

        try {
            Integer pageSize = 0;
            Integer fromElement = 0;
            SequenceOrderType orderType = null;
            String orderProperty = null;
            Status status = null;

            if (searchParameters != null) {
                fromElement = searchParameters.getOffset();
                pageSize = searchParameters.getLimit();
                orderType = searchParameters.getOrderType();
                orderProperty = searchParameters.getOrderProperty();
                status = searchParameters.getStatus();
            }

            List<Relationship> relationships = searchRelationships(serverName, userId,
                    relationshipTypeGUID,
                    searchCriteria,
                    pageSize,
                    fromElement,
                    orderProperty,
                    orderType,
                    status);
            response.setRelationships(converter.toRelationships(relationships));
        } catch (PropertyServerException | RelationshipNotFoundException e) {
            exceptionHandler.captureAssetCatalogExeption(response, e);
        } catch (InvalidParameterException
                | TypeErrorException
                | RepositoryErrorException
                | PropertyErrorException
                | FunctionNotSupportedException
                | UserNotAuthorizedException
                | PagingErrorException e) {
            exceptionHandler.captureOMRSCheckedExceptionBase(response, e);
        }

        return response;
    }

    private Relationship getRelationship(String serverName, String userId, String relationshipId) throws PropertyServerException, InvalidParameterException, RepositoryErrorException, RelationshipNotKnownException, UserNotAuthorizedException, RelationshipNotFoundException {
        OMRSMetadataCollection metadataCollection = assetCatalogInstanceHandler.getMetadataCollection(serverName);

        Relationship relationship = metadataCollection.getRelationship(userId, relationshipId);

        if (relationship == null) {
            AssetCatalogErrorCode errorCode = AssetCatalogErrorCode.RELATIONSHIP_NOT_FOUND;
            String errorMessage = errorCode.getErrorMessageId() +
                    errorCode.getFormattedErrorMessage(relationshipId, serverName);

            throw new RelationshipNotFoundException(errorCode.getHttpErrorCode(),
                    this.getClass().getName(),
                    "getRelationshipById",
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        return relationship;
    }

    private List<Relationship> getRelationshipsByProperty(String serverName, String userId, String relationshipTypeGUID, String matchProperty, String propertyValue, Integer pageSize, Integer fromElement, SequenceOrderType orderType, String orderProperty, Status status) throws PropertyServerException, InvalidParameterException, TypeErrorException, RepositoryErrorException, PropertyErrorException, PagingErrorException, FunctionNotSupportedException, UserNotAuthorizedException, RelationshipNotFoundException {
        OMRSMetadataCollection metadataCollection = assetCatalogInstanceHandler.getMetadataCollection(serverName);

        List<InstanceStatus> limitResultsByStatus = converter.getInstanceStatuses(status);
        SequencingOrder order = converter.getSequencingOrder(orderType);
        InstanceProperties matchProperties = converter.getMatchProperties(matchProperty, propertyValue);

        List<Relationship> relationshipsByProperty = metadataCollection.findRelationshipsByProperty(userId,
                relationshipTypeGUID,
                matchProperties,
                MatchCriteria.ANY,
                fromElement,
                limitResultsByStatus,
                null,
                orderProperty,
                order,
                pageSize);

        if (relationshipsByProperty == null || relationshipsByProperty.isEmpty()) {
            AssetCatalogErrorCode errorCode = AssetCatalogErrorCode.RELATIONSHIPS_WITH_PROPERTY_NOT_FOUND;
            String errorMessage = errorCode.getErrorMessageId() +
                    errorCode.getFormattedErrorMessage(matchProperty, serverName);

            throw new RelationshipNotFoundException(errorCode.getHttpErrorCode(),
                    this.getClass().getName(),
                    "getRelationshipByProperty",
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        return relationshipsByProperty;
    }

    private List<Relationship> searchRelationships(String serverName, String userId, String relationshipTypeGUID, String searchCriteria, Integer pageSize, Integer fromElement, String orderProperty, SequenceOrderType orderType, Status status) throws PropertyServerException, InvalidParameterException, TypeErrorException, RepositoryErrorException, PropertyErrorException, PagingErrorException, FunctionNotSupportedException, UserNotAuthorizedException, RelationshipNotFoundException {
        OMRSMetadataCollection metadataCollection = assetCatalogInstanceHandler.getMetadataCollection(serverName);
        SequencingOrder order = converter.getSequencingOrder(orderType);
        List<InstanceStatus> statusList = converter.getInstanceStatuses(status);

        List<Relationship> relationshipsByPropertyValue = metadataCollection.findRelationshipsByPropertyValue(userId,
                relationshipTypeGUID,
                searchCriteria,
                fromElement,
                statusList,
                null,
                orderProperty,
                order,
                pageSize);

        if (relationshipsByPropertyValue == null || relationshipsByPropertyValue.isEmpty()) {
            AssetCatalogErrorCode errorCode = AssetCatalogErrorCode.RELATIONSHIPS_NOT_FOUND;
            String errorMessage = errorCode.getErrorMessageId() +
                    errorCode.getFormattedErrorMessage(searchCriteria, serverName);

            throw new RelationshipNotFoundException(errorCode.getHttpErrorCode(),
                    this.getClass().getName(),
                    "searchForRelationships",
                    errorMessage,
                    errorCode.getSystemAction(),
                    errorCode.getUserAction());
        }

        return relationshipsByPropertyValue;
    }
}
