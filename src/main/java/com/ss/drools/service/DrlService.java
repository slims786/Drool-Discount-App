package com.ss.drools.service;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.StatefulSession;
import org.drools.core.rule.Package;

import com.ss.drools.model.ProductInfo;

public class DrlService {
	
      public static void main(String[] args) throws Exception {
    	  System.out.println("DrlService.main()");
    	  DrlService service = new DrlService();
    	  service.fireDiscountRule();
	}
      
      public  void fireDiscountRule() throws Exception {
         System.out.println("DrlService.fireDiscountRule()");
    	  //load input files
    	  InputStream inputStream = getClass().getResourceAsStream("/com/ss/drools/rules/discount-rules.drl");
          Reader reader = new InputStreamReader(inputStream);
          
          PackageBuilder pkgBuilder = new PackageBuilder();
          pkgBuilder.addPackageFromDrl(reader);
          Package pkg = pkgBuilder.getPackage();
          
          RuleBase ruleBase = RuleBaseFactory.newRuleBase();
          ruleBase.addPackage(pkg);
          
          //creating input package
          ProductInfo pInfo = new ProductInfo();
          pInfo.setProductType("Diamond");
          
          StatefulSession session = ruleBase.newStatefulSession();
          session.insert(pInfo);
          session.fireAllRules();
          
          System.out.println("========================================");
          System.out.println(pInfo.getDiscount());
          session.dispose();
      }
}
