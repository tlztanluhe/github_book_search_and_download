package com.nexus.git;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;


public class CookbookHelper {
  public static Repository openJGitCookbookRepository() throws IOException {
    FileRepositoryBuilder builder = new FileRepositoryBuilder();

    return builder.readEnvironment() // scan environment GIT_* variables
        .findGitDir() // scan up the file system tree
        .build();
  }

  public static Repository createNewRepository() throws IOException, GitAPIException {
    // prepare a new folder
    // File localPath = File.createTempFile("TestGitRepository", "");
    // if(!localPath.delete()) {
    // throw new IOException("Could not delete temporary file " +
    // localPath);
    // }
    //
    // // create the directory
    // Repository repository = FileRepositoryBuilder.create(new
    // File(localPath, ".git"));
    // repository.create();

    // Git.init().call().
    return Git.cloneRepository()
        .setURI("https://github.com/shootthepoets/information-technology-ebooks.git")
        .setDirectory(new File("D:\\something\\git_dir")).call().getRepository();
  }

  public static void main(String[] args) throws IOException, GitAPIException {
    createNewRepository();
  }
}
