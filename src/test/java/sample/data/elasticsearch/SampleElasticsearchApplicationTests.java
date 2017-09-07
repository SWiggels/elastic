package sample.data.elasticsearch;

import static org.assertj.core.api.Assertions.assertThat;

import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.rule.OutputCapture;

public class SampleElasticsearchApplicationTests {

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Test
	public void testDefaultSettings() throws Exception {
		try {
			new SpringApplicationBuilder(SampleElasticsearchApplication.class).run();
		} catch (Exception ex) {
			if (!elasticsearchRunning(ex)) {
				return;
			}
			throw ex;
		}
		String output = this.outputCapture.toString();
		assertThat(output).contains("firstName='Alice', lastName='Smith'");
	}

	private boolean elasticsearchRunning(Exception ex) {
		Throwable candidate = ex;
		while (candidate != null) {
			if (candidate instanceof NoNodeAvailableException) {
				return false;
			}
			candidate = candidate.getCause();
		}
		return true;
	}

}
