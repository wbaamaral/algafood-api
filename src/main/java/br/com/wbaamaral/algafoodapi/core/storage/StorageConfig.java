package br.com.wbaamaral.algafoodapi.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import br.com.wbaamaral.algafoodapi.core.storage.StorageProperties.TipoStorage;
import br.com.wbaamaral.algafoodapi.domain.service.FotoStorageService;
import br.com.wbaamaral.algafoodapi.infrastructure.service.storage.LocalFotoStorageService;
import br.com.wbaamaral.algafoodapi.infrastructure.service.storage.S3FotoStorageService;

@Configuration
public class StorageConfig {

	@Autowired
	private StorageProperties storageProperties;

	@Bean
	@ConditionalOnProperty(name = "algafood.storage.tipo", havingValue = "s3")
	public AmazonS3 amazonS3() {
		// @formatter:off
		var credentials = new BasicAWSCredentials(storageProperties
				.getS3()
				.getIdChaveAcesso(),
				storageProperties
				.getS3()
				.getChaveAcessoSecreta());

		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.withRegion(storageProperties.getS3().getRegiao())
				.build();
		// @formatter:on
	}

	@Bean
	public FotoStorageService fotoStorageService() {
		if (TipoStorage.S3.equals(storageProperties.getTipo())) {

			return new S3FotoStorageService();
		} else {

			return new LocalFotoStorageService();
		}
	}

}