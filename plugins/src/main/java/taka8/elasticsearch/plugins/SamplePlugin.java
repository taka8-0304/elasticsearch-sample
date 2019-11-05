package taka8.elasticsearch.plugins;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import org.elasticsearch.cluster.metadata.IndexNameExpressionResolver;
import org.elasticsearch.cluster.node.DiscoveryNodes;
import org.elasticsearch.common.settings.ClusterSettings;
import org.elasticsearch.common.settings.IndexScopedSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.SettingsFilter;
import org.elasticsearch.plugins.ActionPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestHandler;

import taka8.elasticsearch.rest.HelloWorldAction;

// REST API用のプラグインを作成する際には、ActionPluginを実装する必要がある。
public class SamplePlugin extends Plugin implements ActionPlugin {

	// このプラグインで定義するRestHandlerのリストを返す
	@Override
	public List<RestHandler> getRestHandlers(Settings settings, RestController restController,
			ClusterSettings clusterSettings, IndexScopedSettings indexScopedSettings, SettingsFilter settingsFilter,
			IndexNameExpressionResolver indexNameExpressionResolver, Supplier<DiscoveryNodes> nodesInCluster) {
		return Arrays.asList(//
				// 独自定義のクラス(Hello World文字列を返却する
				new HelloWorldAction(restController));
	}

}
