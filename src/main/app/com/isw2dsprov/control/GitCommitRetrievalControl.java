package app.com.isw2dsprov.control;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.RefNotAdvertisedException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import app.com.isw2dsprov.entity.Commit;

public class GitCommitRetrievalControl {
	private static final String REPOSPATH = "repo/";
	private static final String CACHERPATH = REPOSPATH + "cache/";
	
	private static final String REFSPATH = "refs/";
	private static final String REFHEADSPATH = REFSPATH + "heads/";
	
	private String locRepoDir;
	private String repUrl;
	private String tk;
	private String prjn;
	private List<Commit> cl;
	
	public GitCommitRetrievalControl(String projName, String remoteUrl, String token) {
		this.repUrl = remoteUrl;
		this.tk = token;
		this.prjn = projName;
		this.locRepoDir = CACHERPATH + prjn + "/";
		System.out.println("Initializing local repository for " + projName);  
		initRepoCache();
		System.out.println("Done.");
		System.out.println("Retrieving commits...");
		this.cl = retrieveCommits();
		
	}
	
	private void printCommitsInfo() {
		for(Commit c : cl) {c.printCommit();}
	}
	
	private List<Commit> retrieveCommits() {
		List<Commit> cl = new ArrayList<Commit>();
		
		
		try(Git git = Git.open(new File(locRepoDir))){
			Repository repository = git.getRepository();
	
			try (RevWalk walk = new RevWalk(repository)) {
			    Ref head = repository.exactRef("HEAD");
			    RevCommit commit = walk.parseCommit(head.getObjectId());
	
			    while (commit != null) {
			    	cl.add(new Commit(commit, prjn));
	
			        commit = commit.getParentCount() > 0 ? walk.parseCommit(commit.getParent(0).getId()) : null;
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return cl;
	}
	
	private void initRepoCache() {
		boolean exi = checkCache();
		if (exi) {
			updateLocalRepo();
			return;
		}
		
		initDirs();
		
		try (Git git = Git.cloneRepository()
                .setURI( repUrl )
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(tk, ""))
                .setDirectory(new File(locRepoDir))
                .call()) {
		} catch (InvalidRemoteException | TransportException e) {
			e.printStackTrace();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
		
	}
	
	private void updateLocalRepo(){
		try (Git git = Git.open(new File(locRepoDir))) {
			//Shall only pull no matter what branch is checked out
            PullCommand pullCmd = git.pull();
            pullCmd.setCredentialsProvider(new UsernamePasswordCredentialsProvider(tk, ""));
            PullResult pr = pullCmd.call();
            if(!pr.isSuccessful()) throw new UnexpectedException("Unknown error: pull: " + REFHEADSPATH);
        } catch (CheckoutConflictException | RefNotAdvertisedException e) {
        	System.out.println(e.getMessage());
        	System.out.println("Could not pull using current log");
        	return;
        } catch (Exception e){
        	e.printStackTrace();
        }
	}
	
	private boolean checkCache() {
		File repof = new File(locRepoDir);
		if(! repof.exists()) return false;
		File dotgitdir = new File(repof, ".git");
		if(! dotgitdir.exists()) return false;

		try(Git git = Git.open(new File(locRepoDir))){
	
			// Get the list of remote repositories
			StoredConfig config = git.getRepository().getConfig();
			Set<String> remoteNames = config.getSubsections("remote");
	
			for (String remoteName : remoteNames) {
			    // Get the RemoteConfig object for the remote repository
			    RemoteConfig remoteConfig = new RemoteConfig(config, remoteName);
	
			    // Get the remote URL from the RemoteConfig object
			    String remoteUrl = remoteConfig.getURIs().get(0).toString();
	
			    if(remoteUrl.equals(repUrl)) return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void initDirs() {
		File f = new File(REPOSPATH);
		if( ! f.exists()) f.mkdir();
		f = new File(CACHERPATH);
		if( ! f.exists()) f.mkdir();
		f = new File(locRepoDir);
		System.out.println(locRepoDir);
		if( ! f.exists()) f.mkdir();
	}
	
	
	public static void main(String[] args) {
		String url = "https://github.com/UniAlessandroP/bookkeeper.git";
		String projname = "BOOKKEEPER";
		String token = "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";
		GitCommitRetrievalControl gcrc = new GitCommitRetrievalControl(projname, url, token);
		gcrc.retrieveCommits();
		gcrc.printCommitsInfo();
	}

	public List<Commit> getCommitList() {
		return this.cl;
	}
}