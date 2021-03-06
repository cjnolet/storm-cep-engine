package org.calrissian.flowbox.example.support;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.generated.StormTopology;
import org.calrissian.flowbox.FlowboxFactory;
import org.calrissian.flowbox.bolt.PrinterBolt;
import org.calrissian.flowbox.model.Event;
import org.calrissian.flowbox.model.kryo.EventSerializer;
import org.calrissian.flowbox.spout.MockEventGeneratorSpout;
import org.calrissian.flowbox.spout.MockFlowLoaderSpout;

public class ExampleRunner {

  FlowProvider provider;

  public ExampleRunner(FlowProvider provider) {
    this.provider = provider;
  }

  public void run() {

    StormTopology topology = new FlowboxFactory(
        new MockFlowLoaderSpout(provider.getFlows(), 60000),
        new MockEventGeneratorSpout(10),
        new PrinterBolt(), 6)
      .createFlowbox()
    .createTopology();

    Config conf = new Config();
    conf.setNumWorkers(20);
    conf.setMaxSpoutPending(5000);
    conf.setDebug(false);
    conf.registerSerialization(Event.class, EventSerializer.class);
    conf.setSkipMissingKryoRegistrations(false);

    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("example-topology", conf, topology);
  }
}
