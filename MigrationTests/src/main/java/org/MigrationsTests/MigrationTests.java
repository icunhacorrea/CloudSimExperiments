package main.java.org.MigrationsTests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterBrokerIndian;
import org.cloudbus.cloudsim.DatacenterBrokerLb;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.File;
import org.cloudbus.cloudsim.HarddriveStorage;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.ParameterException;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

import main.java.org.MigrationsTests.utils.CompareValues;

public class MigrationTests {
	
	private static int fileSize = 10;
	
	private static List<Cloudlet> cloudletList = new ArrayList<Cloudlet>();
	private static List<Vm> vmList = new ArrayList<Vm>();

	public static void main(String args[]) {
		
		if (args.length < 10) {
			Log.printConcatLine("São necessários 10 arquivos de entrada para cada dimensão.");
		} else {
			int indexOfFile = 9;
			
			List<java.io.File> inputs = new ArrayList<java.io.File>();
			DecimalFormat dft = new DecimalFormat("###.####");
			long startTime = 0, stopTime = 0;
			double finishTime = 0;
			
			for (int i = 0; i < args.length; i++)
				inputs.add(new java.io.File(args[indexOfFile]));
			
			startTime = System.currentTimeMillis();
			
			
			//executeSimulation(inputs.get(indexOfFile), indexOfFile);
			//executeSimulationRb(inputs.get(indexOfFile), indexOfFile);
			executeSimulationLb(inputs.get(indexOfFile), indexOfFile);
			
			
			stopTime = System.currentTimeMillis();
			finishTime = (stopTime - startTime) / 1000.00;
			
			Log.printConcatLine(" ******** Tempo de Sim.: ", dft.format(finishTime)
					+ " **********************");
		}
	}

	protected static void executeSimulation(java.io.File input, int index) {
		try {
			int num_user = 1;
			Calendar calendar = Calendar.getInstance();
 			boolean trace_flag = false;
 			
 			CloudSim.init(num_user, calendar, trace_flag);
 			
 			@SuppressWarnings("unused")
 			Datacenter datacenter = createDatacenter("Datacenter_0");
 			
 			DatacenterBroker broker = createDatacenterBroker();
 			
 			int brokerId = broker.getId();
 			
 			CreateVmList(brokerId);
 			CreateCloudletList(input, brokerId);
 			
 			broker.submitCloudletList(cloudletList);
 			broker.submitVmList(vmList);
 			
 			CloudSim.startSimulation();
 			
 			List<Cloudlet> finalExecutionResults = broker.getCloudletReceivedList();
 			
 			CloudSim.stopSimulation();
 			
 			writeOutput(finalExecutionResults, "exec" + index + ".csv");
 			printCloudletList(finalExecutionResults, datacenter.getMigrateCost());
 			
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("Surgiram erros.");
		}
	}
	
	
	protected static void executeSimulationLb(java.io.File input, int index) {
		try {
			int num_user = 1;
			Calendar calendar = Calendar.getInstance();
 			boolean trace_flag = false;
 			
 			CloudSim.init(num_user, calendar, trace_flag);
 			
 			@SuppressWarnings("unused")
 			Datacenter datacenter = createDatacenter("Datacenter_0");
 			
 			DatacenterBrokerLb broker = createDatacenterBrokerLb();
 			
 			int brokerId = broker.getId();
 			
 			Log.printConcatLine("Id do Broker Aquiii: " + brokerId);
 			
 			CreateVmList(brokerId);
 			CreateCloudletList(input, brokerId);
 			
 			broker.submitCloudletList(cloudletList);
 			broker.submitVmList(vmList);
 			
 			CloudSim.startSimulation();
 			
 			List<Cloudlet> finalExecutionResults = broker.getCloudletReceivedList();
 			
 			CloudSim.stopSimulation();
 			
 			findMakeSpan(finalExecutionResults);
 			writeOutput(finalExecutionResults, "exec" + index + ".csv");
 			printCloudletList(finalExecutionResults, datacenter.getMigrateCost());
 			
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("Surgiram erros.");
		}
	}
	
	
	protected static void executeSimulationRb(java.io.File input, int index) {
		try {
			int num_user = 1;
			Calendar calendar = Calendar.getInstance();
 			boolean trace_flag = false;
 			
 			CloudSim.init(num_user, calendar, trace_flag);
 			
 			@SuppressWarnings("unused")
 			Datacenter datacenter = createDatacenter("Datacenter_0");
 			
 			DatacenterBrokerIndian broker = createDatacenterBrokerIndian();
 			
 			int brokerId = broker.getId();
 			
 			CreateVmList(brokerId);
 			CreateCloudletList(input, brokerId);
 			
 			broker.submitCloudletList(cloudletList);
 			broker.submitVmList(vmList);
 			
 			CloudSim.startSimulation();
 			
 			List<Cloudlet> finalExecutionResults = broker.getCloudletReceivedList();
 			
 			CloudSim.stopSimulation();
 			
 			findMakeSpan(finalExecutionResults);
 			writeOutput(finalExecutionResults, "exec" + index + ".csv");
 			printCloudletList(finalExecutionResults, datacenter.getMigrateCost());
 			
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("Surgiram erros.");
		}
	}
	
	
	private static Datacenter createDatacenter(String name) {
		LinkedList<Storage> storageList = new LinkedList<Storage>();
		HarddriveStorage hd = null;
		
		File file1 = null;
		try {
			hd = new HarddriveStorage(20000);
			file1 = new File("file.txt", fileSize);
			
		} catch (ParameterException e) {
			e.printStackTrace();
		}
		
		hd.addFile(file1);
		storageList.add(hd);
		
		List<Pe> peList0 = new ArrayList<Pe>();
		List<Pe> peList1 = new ArrayList<Pe>();
		List<Pe> peList2 = new ArrayList<Pe>();
		List<Pe> peList3 = new ArrayList<Pe>();
		List<Pe> peList4 = new ArrayList<Pe>();
		
		List<Host> hostList = new ArrayList<Host>();
		
		peList0.add(new Pe(0, new PeProvisionerSimple(2000)));
		peList1.add(new Pe(1, new PeProvisionerSimple(3000)));
		peList2.add(new Pe(2, new PeProvisionerSimple(2000)));
		peList3.add(new Pe(3, new PeProvisionerSimple(2000)));
		peList4.add(new Pe(4, new PeProvisionerSimple(2000)));
	
		int ram = 4096; // host memory (MB)
		long storage = 1000000; // host storage
		int bw = 10000;
		
		hostList.add(
				new Host(
					0,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList0,
					new VmSchedulerTimeShared(peList0)
				)
			);
		
		hostList.add(
				new Host(
					1,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList1,
					new VmSchedulerTimeShared(peList1)
				)
			);
		
		/*hostList.add(
				new Host(
					2,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList2,
					new VmSchedulerTimeShared(peList2)
				)
			);
		
		hostList.add(
				new Host(
					3,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList3,
					new VmSchedulerTimeShared(peList3)
				)
			);
		
		hostList.add(
				new Host(
					4,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList4,
					new VmSchedulerTimeShared(peList4)
				)
			);*/
		
		String arch = "x86"; // system architecture
		String os = "Linux"; // operating system
		String vmm = "Xen";
		double time_zone = 10.0; // time zone this resource located
		double cost = 3.0; // the cost of using processing in this resource
		double costPerMem = 1.0; // the cost of using memory in this resource
		double costPerStorage = 0.05; // the cost of using storage in this
		double costPerBw = 0.01; // the cost of using bw in this resource

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
				arch, os, vmm, hostList, time_zone, cost, costPerMem,
				costPerStorage, costPerBw);

		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return datacenter;
	}
	
	
	private static DatacenterBroker createDatacenterBroker() {
		DatacenterBroker broker = null;
		
		try {
			broker = new DatacenterBroker("broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}
	
	
	private static DatacenterBrokerLb createDatacenterBrokerLb() {
		DatacenterBrokerLb broker = null;
		
		try {
			broker = new DatacenterBrokerLb("broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}
	
	
	private static DatacenterBrokerIndian createDatacenterBrokerIndian () {
		DatacenterBrokerIndian broker = null;
		
		try {
			broker = new DatacenterBrokerIndian("broker");
		} catch (Exception e) {
			return null;
		}
		return broker;
	}
	
	
	private static void CreateVmList(int brokerId) {
		vmList.add(new Vm(
				0,
				brokerId,
				500,
				1,
				250,
				250,
				5000,
				"XEN",
				new CloudletSchedulerSpaceShared()
			));
		
		vmList.add(new Vm(
				1,
				brokerId,
				1000,
				1,
				250,
				250,
				5000,
				"XEN",
				new CloudletSchedulerSpaceShared()
			));
		
		vmList.add(new Vm(
				2,
				brokerId,
				1500,
				1,
				250,
				250,
				5000,
				"XEN",
				new CloudletSchedulerSpaceShared()
			));
		
		vmList.add(new Vm(
				3,
				brokerId,
				2000,
				1,
				250,
				250,
				5000,
				"XEN",
				new CloudletSchedulerSpaceShared()
			));
		
		/*vmList.add(new Vm(
				4,
				brokerId,
				1200,
				1,
				250,
				250,
				5000,
				"XEN",
				new CloudletSchedulerSpaceShared()
			));
		
		vmList.add(new Vm(
				5,
				brokerId,
				1400,
				1,
				250,
				250,
				5000,
				"XEN",
				new CloudletSchedulerSpaceShared()
			));*/
	}
	
	
	private static void CreateCloudletList(java.io.File input, int brokerId) throws NumberFormatException, IOException {
		UtilizationModelFull fullUtilize = new UtilizationModelFull();
		List<String> fileList = new ArrayList<String>();
		fileList.add("file.txt");
		
		BufferedReader buffer = new BufferedReader(new FileReader(input));
		int numberOfCloudlets = Integer.parseInt(buffer.readLine());
		
		for(int cloudletId = 0; cloudletId < numberOfCloudlets; cloudletId++) {
			
			Cloudlet newCloudlet = new Cloudlet(cloudletId, Integer.parseInt(buffer.readLine()), 1, 
					300, 400, fullUtilize, fullUtilize, fullUtilize);
			
			newCloudlet.setUserId(brokerId);
			cloudletList.add(newCloudlet);
		}
		buffer.close();
	}
	
	
	@SuppressWarnings("unused")
	private static void printCloudletList(List<Cloudlet> list, double migrationCost) {
		// Ordenar cloudlets by vm.
		Collections.sort(list, new CompareValues());
		
		double totalFinishTime = 0;
		double totalCPUTime = 0;
		double makeSpan = 0;
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent
				+ "Data center ID" + indent + "VM ID" + indent + "Time" + indent
				+ "Start Time" + indent + "Finish Time" + indent + "Submission Time"
				+ indent + "Cloudlet Length");

		DecimalFormat dft = new DecimalFormat("###.####");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			totalCPUTime += cloudlet.getActualCPUTime();
			totalFinishTime += cloudlet.getFinishTime();
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getStatus() == Cloudlet.SUCCESS) {
				Log.print("SUCCESS");

				Log.printLine(indent + indent + cloudlet.getResourceId()
						+ indent + indent + indent + cloudlet.getVmId()
						+ indent + indent
						+ dft.format(cloudlet.getActualCPUTime()) + indent
						+ indent + dft.format(cloudlet.getExecStartTime())
						+ indent + indent
						+ dft.format(cloudlet.getFinishTime())
						+ indent + indent + indent + indent
						+ dft.format(cloudlet.getSubmissionTime())
						+ indent + indent + indent + indent
						+dft.format(cloudlet.getCloudletTotalLength()));
			}
		}
		
		findMakeSpan(list);
		
		Log.printConcatLine(" ******** TEMPO TOTAL DE EXECUÇÃO: ", dft.format(totalFinishTime), " ********");
		Log.printConcatLine(" ******** TEMPO TOTAL DE CPU: ", dft.format(totalCPUTime), " ********");
		Log.printConcatLine(" ******** CUSTO TOTAL DE MIGRAÇÃO: ", dft.format(migrationCost), " ********");
	}
	
	
	private static void writeOutput(List<Cloudlet> list, String name) {
		Collections.sort(list, new CompareValues());
		
		try (PrintWriter writer = new PrintWriter(new java.io.File(name))) {
			StringBuilder sb = new StringBuilder();
			DecimalFormat dft = new DecimalFormat("###.####");
			/*sb.append("#Cloudlet ID,");
			sb.append("Status,");
			sb.append("Datacenter ID,");
			sb.append("Vm ID,");
			sb.append("Time,");
			sb.append("Finish Time,");
			sb.append("Submission Time");
			sb.append("\n");*/
			
			for (Cloudlet cl : list) {
				sb.append(dft.format(cl.getCloudletId()) + ",");
				sb.append("SUCCESS,");
				sb.append(name.split("\\.")[0] + ",");
				sb.append(fileSize + ",");
				sb.append(dft.format(cl.getVmId()) + ",");
				sb.append(dft.format(cl.getActualCPUTime()).replace(",", ".") + ",");
				sb.append(dft.format(cl.getFinishTime()).replace(",", ".") + ",");
				sb.append(dft.format(cl.getSubmissionDelay()).replace(",", "."));
				sb.append("\n");
			 }
			 
			 writer.write(sb.toString());
			 //Log.printConcatLine("\nCSV de saída escrito.");
			 
		} catch (FileNotFoundException e) {
			Log.printConcatLine(e.getMessage());
		}
	}
	
	
	protected static void findMakeSpan(List<Cloudlet> list) {
		double makeSpan = Double.MIN_VALUE;
		DecimalFormat dft = new DecimalFormat("###.####");
		for (Cloudlet c : list) {
			if (c.getFinishTime() > makeSpan)
				makeSpan = c.getFinishTime();
		}
		Log.printConcatLine("\n ******** MAKESPAN: ", dft.format(makeSpan)
				+ " **********************");
	}
}
