/* SPDX-License-Identifier: Apache-2.0 */
/* Copyright Contributors to the ODPi Egeria project. */
package org.odpi.openmetadata.repositoryservices.eventmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditCode;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditLog;
import org.odpi.openmetadata.repositoryservices.auditlog.OMRSAuditingComponent;
import org.odpi.openmetadata.repositoryservices.events.*;
import org.odpi.openmetadata.repositoryservices.events.beans.v1.OMRSEventV1;
import org.odpi.openmetadata.repositoryservices.connectors.omrstopic.OMRSTopicListener;

/**
 * <p>
 * The OMRSEventListener manages inbound events from the metadata repository cohort.  There are
 * two main groups of events: registration events that are handled by the OMRSCohortRegistry and metadata
 * events that describe changes to TypeDefs and metadata instances.  The metadata events are handled by the
 * local connector.
 * </p>
 * <p>
 * The role of the OMRSEventListener is to decide which events to process.  This is controlled by the
 * synchronization rule passed on the constructor.
 * </p>
 */
public class OMRSEventListener implements OMRSTopicListener
{
    private String cohortName;
    private String localMetadataCollectionId;

    /*
     * There is an event processor for each category of event.  The OMRSEventListener passes appropriate events to these
     * objects depending on the settings of its configuration.
     */
    private OMRSRegistryEventProcessor registryEventProcessor;
    private OMRSTypeDefEventProcessor  typeDefEventProcessor;
    private OMRSInstanceEventProcessor instanceEventProcessor;

    /*
     * The audit log is used for recording events, decisions, errors and exceptions
     */
    private  OMRSAuditLog  auditLog;

    private static final Logger log = LoggerFactory.getLogger(OMRSEventListener.class);


    /**
     * Default Constructor saves configuration parameters.
     *
     * @param cohortName name of the cohort that this event listener belongs to
     * @param localMetadataCollectionId unique identifier for the local metadata collection
     * @param registryEventProcessor processor for registry events
     * @param typeDefEventProcessor processor for TypeDef synchronization events
     * @param instanceEventProcessor processor for metadata instance replication
     * @param auditLog audit log for this component.
     */
    public OMRSEventListener(String                                cohortName,
                             String                                localMetadataCollectionId,
                             OMRSRegistryEventProcessor            registryEventProcessor,
                             OMRSTypeDefEventProcessor             typeDefEventProcessor,
                             OMRSInstanceEventProcessor            instanceEventProcessor,
                             OMRSAuditLog                          auditLog)
    {
        this.cohortName = cohortName;
        this.localMetadataCollectionId = localMetadataCollectionId;
        this.registryEventProcessor = registryEventProcessor;
        this.typeDefEventProcessor  = typeDefEventProcessor;
        this.instanceEventProcessor = instanceEventProcessor;
        this.auditLog = auditLog;

        final String   actionDescription = "Initialize OMRS Event Listener";

        log.debug(actionDescription);

        OMRSAuditCode auditCode = OMRSAuditCode.OMRS_LISTENER_INITIALIZING;
        auditLog.logRecord(actionDescription,
                           auditCode.getLogMessageId(),
                           auditCode.getSeverity(),
                           auditCode.getFormattedLogMessage(cohortName),
                           null,
                           auditCode.getSystemAction(),
                           auditCode.getUserAction());
    }


    /**
     * The event contains a registry event.  It needs to be further unpacked and passed to the appropriate
     * registry event processor (OMRSCohortRegistry).
     *
     * @param registryEvent event to unpack
     */
    public void processRegistryEvent(OMRSRegistryEvent   registryEvent)
    {
        log.debug("Processing registry event: " + registryEvent);

        if (registryEvent == null)
        {
            log.debug("Null registry event; ignoring event");
        }
        else if ((localMetadataCollectionId != null) &&
                 (localMetadataCollectionId.equals(registryEvent.getEventOriginator().getMetadataCollectionId())))
        {
            log.debug("Ignoring event that this server originated");
        }
        else if (registryEventProcessor == null)
        {
            log.debug("No registry event processor; ignoring event ");
        }
        else /* process registry event */
        {
            OMRSRegistryEventType registryEventType       = registryEvent.getRegistryEventType();
            OMRSEventOriginator   registryEventOriginator = registryEvent.getEventOriginator();

            if ((registryEventType != null) && (registryEventOriginator != null))
            {
                switch (registryEventType)
                {
                    case REGISTRATION_EVENT:
                        registryEventProcessor.processRegistrationEvent(cohortName,
                                                                        registryEventOriginator.getMetadataCollectionId(),
                                                                        registryEventOriginator.getServerName(),
                                                                        registryEventOriginator.getServerType(),
                                                                        registryEventOriginator.getOrganizationName(),
                                                                        registryEvent.getRegistrationTimestamp(),
                                                                        registryEvent.getRemoteConnection());
                        break;

                    case RE_REGISTRATION_EVENT:
                        registryEventProcessor.processReRegistrationEvent(cohortName,
                                                                          registryEventOriginator.getMetadataCollectionId(),
                                                                          registryEventOriginator.getServerName(),
                                                                          registryEventOriginator.getServerType(),
                                                                          registryEventOriginator.getOrganizationName(),
                                                                          registryEvent.getRegistrationTimestamp(),
                                                                          registryEvent.getRemoteConnection());
                        break;

                    case REFRESH_REGISTRATION_REQUEST:
                        registryEventProcessor.processRegistrationRefreshRequest(cohortName,
                                                                                 registryEventOriginator.getServerName(),
                                                                                 registryEventOriginator.getServerType(),
                                                                                 registryEventOriginator.getOrganizationName());
                        break;

                    case UN_REGISTRATION_EVENT:
                        registryEventProcessor.processUnRegistrationEvent(cohortName,
                                                                          registryEventOriginator.getMetadataCollectionId(),
                                                                          registryEventOriginator.getServerName(),
                                                                          registryEventOriginator.getServerType(),
                                                                          registryEventOriginator.getOrganizationName());
                        break;

                    case REGISTRATION_ERROR_EVENT:
                        OMRSRegistryEventErrorCode errorCode = registryEvent.getErrorCode();

                        if (errorCode != null)
                        {
                            switch(errorCode)
                            {
                                case BAD_REMOTE_CONNECTION:
                                    registryEventProcessor.processBadConnectionEvent(cohortName,
                                                                                     registryEventOriginator.getMetadataCollectionId(),
                                                                                     registryEventOriginator.getServerName(),
                                                                                     registryEventOriginator.getServerType(),
                                                                                     registryEventOriginator.getOrganizationName(),
                                                                                     registryEvent.getTargetMetadataCollectionId(),
                                                                                     registryEvent.getTargetRemoteConnection(),
                                                                                     registryEvent.getErrorMessage());
                                    break;

                                case CONFLICTING_COLLECTION_ID:
                                    registryEventProcessor.processConflictingCollectionIdEvent(cohortName,
                                                                                               registryEventOriginator.getMetadataCollectionId(),
                                                                                               registryEventOriginator.getServerName(),
                                                                                               registryEventOriginator.getServerType(),
                                                                                               registryEventOriginator.getOrganizationName(),
                                                                                               registryEvent.getTargetMetadataCollectionId(),
                                                                                               registryEvent.getErrorMessage());
                                    break;

                                default:
                                    log.debug("Unknown registry event error code; ignoring event");
                                    break;
                            }
                        }
                        else
                        {
                            log.debug("Null registry event error code, ignoring event");
                        }
                        break;

                    default:
                        /*
                         * New type of registry event that this server does not understand ignore it
                         */
                        log.debug("Unknown registry event: " + registryEvent.toString());
                        break;
                }
            }
            else
            {
                log.debug("Ignored registry event: " + registryEvent.toString());
            }
        }
    }


    /**
     * Unpack and deliver a TypeDef event to the TypeDefEventProcessor
     *
     * @param typeDefEvent event to unpack
     */
    public void processTypeDefEvent(OMRSTypeDefEvent   typeDefEvent)
    {
        if (typeDefEvent == null)
        {
            log.debug("Null TypeDef event; ignoring event");
        }
        else if ((localMetadataCollectionId != null) &&
                (localMetadataCollectionId.equals(typeDefEvent.getEventOriginator().getMetadataCollectionId())))
        {
            log.debug("Ignoring event that this server originated");
        }
        else if (typeDefEventProcessor == null)
        {
            log.debug("No TypeDef event processor; ignoring event");
        }
        else
        {
            typeDefEventProcessor.sendTypeDefEvent(cohortName, typeDefEvent);
        }
    }


    /**
     * Unpack and deliver an instance event to the InstanceEventProcessor
     *
     * @param instanceEvent event to unpack
     */
    public void processInstanceEvent(OMRSInstanceEvent  instanceEvent)
    {
        log.debug("Processing instance event: " + instanceEvent);

        if (instanceEvent == null)
        {
            log.debug("Null instance event ignoring event");
        }
        else if ((localMetadataCollectionId != null) &&
                (localMetadataCollectionId.equals(instanceEvent.getEventOriginator().getMetadataCollectionId())))
        {
            log.debug("Ignoring event that this server originated");
        }
        else if (instanceEventProcessor == null)
        {

        }
        else
        {
            instanceEventProcessor.sendInstanceEvent(cohortName, instanceEvent);
        }
    }



}



