package com.tekion.tenant.MultiTenant_Prj.multiTenant;

public class TenantResolvingException extends Exception {
	public TenantResolvingException(Throwable throwable, String message) {
		super(message, throwable);
	}
}
