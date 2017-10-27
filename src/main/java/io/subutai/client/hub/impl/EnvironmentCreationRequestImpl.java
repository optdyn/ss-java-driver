package io.subutai.client.hub.impl;


import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import io.subutai.client.hub.api.Container.ContainerSize;
import io.subutai.client.hub.api.EnvironmentCreationRequest;


public class EnvironmentCreationRequestImpl implements EnvironmentCreationRequest
{
    private final String environmentName;

    private final List<Node> nodes;

    private Boolean exchangeSshKeys = true;

    private Boolean registerHosts = true;


    EnvironmentCreationRequestImpl( final String environmentName )
    {
        Preconditions.checkArgument( !StringUtil.isBlank( environmentName ) );

        this.environmentName = environmentName;
        this.nodes = Lists.newArrayList();
    }


    public void addNode( final String hostname, final String templateId, final ContainerSize containerSize,
                         final String peerId, final String resourceHostId )
    {
        this.nodes.add( new Node( hostname, templateId, containerSize, peerId, resourceHostId ) );
    }


    List<Node> getNodes()
    {
        return nodes;
    }


    static class ContainerQuota
    {
        private final ContainerSize containerSize;


        ContainerQuota( final ContainerSize containerSize )
        {
            Preconditions.checkNotNull( containerSize );

            this.containerSize = containerSize;
        }
    }


    static class Node
    {
        private final String hostname;

        private final String templateId;

        private String templateName;//workaround

        private final ContainerQuota quota;

        private final String peerId;

        private final String resourceHostId;


        Node( final String hostname, final String templateId, final ContainerSize containerSize, final String peerId,
              final String resourceHostId )
        {
            Preconditions.checkArgument( !StringUtil.isBlank( hostname ) );
            Preconditions.checkArgument( !StringUtil.isBlank( templateId ) );
            Preconditions.checkArgument( !StringUtil.isBlank( peerId ) );
            Preconditions.checkArgument( !StringUtil.isBlank( resourceHostId ) );
            Preconditions.checkNotNull( containerSize );

            this.hostname = hostname;
            this.templateId = templateId;
            this.quota = new ContainerQuota( containerSize );
            this.peerId = peerId;
            this.resourceHostId = resourceHostId;
        }


        String getTemplateId()
        {
            return templateId;
        }


        String getTemplateName()
        {
            return templateName;
        }


        void setTemplateName( final String templateName )
        {
            this.templateName = templateName;
        }
    }
}
