package com.example.app;

import com.example.Readers.IReader;
import com.example.Readers.RemoteSourceReader;
import com.example.Readers.XmlReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	@Bean
	public IReader remoteResourceReader() {
		return new RemoteSourceReader();
	}

	@Bean
	public IReader xmlReader() {
		return new XmlReader();
	}
}
