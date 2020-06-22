package com.jacob.spring.apollo.init;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Properties;

@Component
public class ApolloConfigUtil {

	private static final Logger logger = LoggerFactory.getLogger(ApolloConfigUtil.class);
	private static String DEFAULT_VALUE = "undefined";

	public static Properties exec(String[] namespace) {
		final Properties properties = new Properties();
		for (int i = 0; i < namespace.length; i++) {
			Config config = ConfigService.getConfig(namespace[i]);
			logger.info("~~~~~~~~~~~开始初始化Apollo平台变量~~~~~~~~~~~~~~~~~~~~~~~~~{}", namespace[i]);
			for (String key : config.getPropertyNames()) {
				String value = config.getProperty(key, DEFAULT_VALUE);
				properties.put(key, value);
				logger.info("key:{}   ======  value:{}", key, value);
			}

			config.addChangeListener(new ConfigChangeListener() {

				@Override
				public void onChange(ConfigChangeEvent changeEvent) {
					logger.info("Changes for namespace {}", changeEvent.getNamespace());
					for (String key : changeEvent.changedKeys()) {
						ConfigChange change = changeEvent.getChange(key);
						properties.put(key, change.getNewValue());
						logger.info("Change - key: {}, oldValue: {}, newValue: {}, changeType: {}",
								change.getPropertyName(), change.getOldValue(), change.getNewValue(),
								change.getChangeType());
					}

				}
			});
			logger.info("~~~~~~~~~~~结束初始化Apollo平台变量~~~~~~~~~~~~~~~~~~~~~~~~~{}", namespace[i]);
		}
		return properties;

	}

}
