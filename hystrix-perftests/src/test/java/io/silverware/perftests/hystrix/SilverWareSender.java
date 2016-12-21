/*
 * -----------------------------------------------------------------------\
 * SilverWare
 *
 * Copyright (C) 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -----------------------------------------------------------------------/
 */
package io.silverware.perftests.hystrix;

import org.perfcake.PerfCakeException;
import org.perfcake.message.Message;
import org.perfcake.message.sender.AbstractSender;
import org.perfcake.reporting.MeasurementUnit;

import java.io.Serializable;
import java.util.Properties;

public class SilverWareSender extends AbstractSender {

   static CounterMicroservice counterMicroservice;

   @Override
   public void doInit(final Properties properties) throws PerfCakeException {

   }

   @Override
   public void doClose() throws PerfCakeException {

   }

   @Override
   public Serializable doSend(final Message message, final MeasurementUnit measurementUnit) throws Exception {
      return counterMicroservice.count();
   }

}
