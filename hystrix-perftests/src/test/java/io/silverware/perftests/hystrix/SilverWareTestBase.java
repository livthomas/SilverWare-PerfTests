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
import io.silverware.microservices.util.BootUtil;

import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import javax.enterprise.inject.spi.BeanManager;

public class SilverWareTestBase {

   protected static final String HYSTRIX_SCENARIO = new File(SilverWareTestBase.class.getClassLoader().getResource("scenarios/hystrix.xml").getFile()).getAbsolutePath();

   private BootUtil bootUtil;
   private Thread platform;

   private final String[] packages;

   public SilverWareTestBase(final Package... packages) {
      this.packages = Arrays.stream(packages)
                            .map(Package::getName)
                            .toArray(String[]::new);
   }

   @Before
   public void setUp() throws InterruptedException {
      bootUtil = new BootUtil();

      platform = bootUtil.getMicroservicePlatform(packages);
      platform.start();

      waitForBeanManager();
   }

   @After
   public void tearDown() throws InterruptedException {
      if (platform != null) {
         platform.interrupt();
         platform.join();
         platform = null;
      }
   }

   protected <T> T lookupBean(Class<T> type, Annotation... qualifiers) {
      return ((CdiMicroserviceProvider) bootUtil.getContext().getProvider(CdiMicroserviceProvider.class)).lookupBean(type, qualifiers);
   }

   private BeanManager waitForBeanManager() throws InterruptedException {
      BeanManager beanManager = null;
      while (beanManager == null) {
         beanManager = (BeanManager) bootUtil.getContext().getProperties().get(CdiMicroserviceProvider.BEAN_MANAGER);
         Thread.sleep(200);
      }

      return beanManager;
   }
}
