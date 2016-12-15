package com.ysu.zyw.tc.mvn.plugin;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;


@Mojo(name = "build-static-resource")
public class TcStaticResourceMavenPlugin extends AbstractMojo {

    @Parameter
    private String resourceOutputDirectory;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            FileUtils.forceMkdir(new File(resourceOutputDirectory));
            try (OutputStream os = new FileOutputStream(new File(resourceOutputDirectory + "/helo"))) {
                IOUtils.write("afsdfasdsdfaasdfsdfa", os, Charset.forName("UTF-8"));
            }
        } catch (Exception e) {
            getLog().error("", e);
        }
        getLog().info(resourceOutputDirectory);
    }

}
