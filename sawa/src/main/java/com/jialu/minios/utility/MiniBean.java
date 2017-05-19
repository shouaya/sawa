
package com.jialu.minios.utility;

import java.io.InputStream;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.jialu.minios.configuration.MiniConfiguration;

import io.dropwizard.hibernate.AbstractDAO;

public class MiniBean extends MiniConfiguration{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MiniBean.class);
	
	private AmazonS3 amazonS3Service;
	
	private HashMap<String, AbstractDAO<?>> daoList = new HashMap<String, AbstractDAO<?>>();
			
	@SuppressWarnings("unchecked")
	public <T> T getDao(Class<T> type) {
		AbstractDAO<?> baseDao = daoList.get(type.getName());
		return (T) baseDao;
	}
	
	public HashMap<String, AbstractDAO<?>> getDaoList() {
		return daoList;
	}

	public void setDaoList(HashMap<String, AbstractDAO<?>> daoList) {
		this.daoList = daoList;
	}

	public AmazonS3 getAmazonS3Service() {
		return amazonS3Service;
	}

	public void setAmazonS3Service(AmazonS3 amazonS3Service) {
		this.amazonS3Service = amazonS3Service;
	}
	
	public String upload2S3(String key, long contentSize, InputStream in){
		String url = MiniConstants.AMAZONAWS_S3_URL + super.getAmazons3().getBucket();
		try {
			ObjectMetadata putMetaData = new ObjectMetadata();
			putMetaData.setContentLength(contentSize);
			this.amazonS3Service.putObject(
					new PutObjectRequest(super.getAmazons3().getBucket(), key, in, putMetaData)
					.withCannedAcl(CannedAccessControlList.PublicRead));
			url = url + "/" + key;
		} catch (AmazonServiceException ase) {
			LOGGER.error("uploadToS3" ,ase);
		} catch (AmazonClientException ace) {
			LOGGER.error("uploadToS3" ,ace);
		}
		return url;
	}

}
