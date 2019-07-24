package main.java.org.MigrationsTests.utils;

import java.util.Comparator;

import org.cloudbus.cloudsim.Cloudlet;

public class CompareValues implements Comparator<Cloudlet> {
	public int compare(Cloudlet cl1, Cloudlet cl2) {
		return cl1.getVmId() - cl2.getVmId();
	}
}