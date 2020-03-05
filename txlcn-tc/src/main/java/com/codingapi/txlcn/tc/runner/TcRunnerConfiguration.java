package com.codingapi.txlcn.tc.runner;

import com.codingapi.txlcn.protocol.ProtocolServer;
import com.codingapi.txlcn.tc.config.TxConfig;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TcRunnerConfiguration {

  @Autowired
  private TMServerRunner tmServerRunner;

  @Bean
  public TMServerRunner tmServerRunner(TxConfig txConfig, ProtocolServer protocolServer) {
    return new TMServerRunner(txConfig, protocolServer);
  }

  @PostConstruct
  public void start() {
    tmServerRunner.init();
  }

}
