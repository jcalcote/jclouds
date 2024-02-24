/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.rackspace.cloudloadbalancers.v1.features;

import jakarta.inject.Named;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.jclouds.Fallbacks.FalseOnNotFoundOr404;
import org.jclouds.Fallbacks.FalseOnNotFoundOr422;
import org.jclouds.Fallbacks.NullOnNotFoundOr404;
import org.jclouds.Fallbacks.VoidOnNotFoundOr404;
import org.jclouds.openstack.keystone.auth.filters.AuthenticateRequest;
import org.jclouds.rackspace.cloudloadbalancers.v1.domain.ConnectionThrottle;
import org.jclouds.rackspace.cloudloadbalancers.v1.functions.ParseNestedBoolean;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.Payload;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.WrapWith;

/**
 * Connection management features.
 * <p/>
 */
@RequestFilters(AuthenticateRequest.class)
public interface ConnectionApi {
   /**
    * The connection throttling feature imposes limits on the number of connections per IP address to help mitigate 
    * malicious or abusive traffic to your applications.
    */
   @Named("connectionthrottle:create")
   @PUT
   @Consumes(MediaType.APPLICATION_JSON) 
   @Fallback(VoidOnNotFoundOr404.class)
   @Path("/connectionthrottle")
   void createOrUpdateConnectionThrottle(
         @WrapWith("connectionThrottle") ConnectionThrottle connectionThrottle);

   /**
    * Get connection throttle.
    */
   @Named("connectionthrottle:get")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @SelectJson("connectionThrottle")
   @Fallback(NullOnNotFoundOr404.class)
   @Path("/connectionthrottle")
   ConnectionThrottle getConnectionThrottle();
   
   /**
    * Delete connection throttle.
    * 
    * @return true on a successful delete, false if the connection throttle was not found.
    */
   @Named("connectionthrottle:delete")
   @DELETE
   @Fallback(FalseOnNotFoundOr422.class)
   @Path("/connectionthrottle")
   @Consumes("*/*")
   boolean deleteConnectionThrottle();
   
   /**
    * Determine if the load balancer is logging connections.
    */
   @Named("connectionlogging:state")
   @GET
   @Consumes(MediaType.APPLICATION_JSON)
   @ResponseParser(ParseNestedBoolean.class)
   @Fallback(FalseOnNotFoundOr404.class)
   @Path("/connectionlogging")
   boolean isConnectionLogging();
   
   /**
    * Enable logging connections.
    */
   @Named("connectionlogging:state")
   @PUT
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Fallback(VoidOnNotFoundOr404.class)
   @Payload("{\"connectionLogging\":{\"enabled\":true}}")
   @Path("/connectionlogging")
   void enableConnectionLogging();
   
   /**
    * Disable logging connections.
    */
   @Named("connectionlogging:state")
   @PUT
   @Produces(MediaType.APPLICATION_JSON)
   @Consumes(MediaType.APPLICATION_JSON)
   @Fallback(VoidOnNotFoundOr404.class)
   @Payload("{\"connectionLogging\":{\"enabled\":false}}")
   @Path("/connectionlogging")
   void disableConnectionLogging();
}
