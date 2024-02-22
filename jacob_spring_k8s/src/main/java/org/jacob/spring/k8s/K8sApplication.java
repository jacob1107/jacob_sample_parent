package org.jacob.spring.k8s;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@SpringBootApplication
public class K8sApplication {

    public static void main(String[] args) {
        System.out.println("hello");
        try {
            KubernetesClient client = getKubernetesClient();
            getNameSpace(client);
            //createNameSpace(client);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createNameSpace(KubernetesClient client) {
        System.err.println("===========createNameSpace======");
        Namespace namespace = new NamespaceBuilder()
                .withNewMetadata()
                .withName("java-k8s")
                // .addToLabels("reason", "pkslow-sample")
                .endMetadata()
                .build();
        client.namespaces().createOrReplace(namespace);
    }

    private static void getNameSpace(KubernetesClient client) {
        // 查看命名空间
        NamespaceList namespaceList = client.namespaces().list();
        namespaceList.getItems()
                .forEach(namespace ->
                        System.out.println(namespace.getMetadata().getName() + ":" + namespace.getStatus().getPhase()));
    }


    private static KubernetesClient getKubernetesClient() throws IOException {
        File configFile = new File("E:/config");
        String configYaml = String.join("\n", Files.readAllLines(configFile.toPath()));
        Config config = Config.fromKubeconfig(configYaml);
        config.setTrustCerts(true);
        return new DefaultKubernetesClient(config);
    }
}
