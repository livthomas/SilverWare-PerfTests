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

import io.silverware.microservices.providers.cdi.CdiMicroserviceProvider;

import org.junit.Test;
import org.perfcake.PerfCakeException;
import org.perfcake.scenario.Scenario;
import org.perfcake.scenario.ScenarioLoader;

public class CdiPerformanceTest extends SilverWareTestBase {

   public CdiPerformanceTest() {
      super(CounterMicroservice.class.getPackage(), CdiMicroserviceProvider.class.getPackage());
   }

   @Test
   public void testPureCDI() throws PerfCakeException {
      CdiCounterMicroservice cdiCounterMicroservice = lookupBean(CdiCounterMicroservice.class);
      SilverWareSender.counterMicroservice = cdiCounterMicroservice.getCounterMicroservice();

      Scenario scenario = ScenarioLoader.load(HYSTRIX_SCENARIO);
      scenario.init();
      scenario.run();
      scenario.close();
   }
}
