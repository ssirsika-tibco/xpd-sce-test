FROM centos:7.6.1810@sha256:6ae4cddb2b37f889afd576a17a5286b311dcbf10a904409670827f6f9b50065e as installer
LABEL maintainer="TIBCO Software Inc" \
      image="Installation" \
      copyright="(c) TIBCO Software Inc 2004 - 2019. All rights reserved."

# install required software
# RUN yum -y -q install java-1.8.0-openjdk && \
RUN yum -y -q install unzip  && \
    groupadd -r studio && useradd -r -m -g studio studio  && \
    mkdir -p /opt/TIBCO/studio-cloud-bpm /tmp/tibco/  && \
    chown -R studio:studio /opt/TIBCO/studio-cloud-bpm /tmp/tibco/

USER studio:studio

# copy and un-zip installer into /tmp/tibco and run installation
COPY TIB_business-studio-cloud-bpm-edition_?.?.?_linux*.zip /tmp/tibco/TIB_business-studio-cloud-bpm-edition.zip
COPY docker-studio.silent /tmp/tibco/
WORKDIR /tmp/tibco
RUN unzip -q TIB_business-studio-cloud-bpm-edition.zip && \
    ./TIBCOUniversalInstaller-lnx-x86-64.bin -silent -V responseFile="docker-studio.silent"

# initialize the eclipse configuration - to improve start-up performance
WORKDIR /opt/TIBCO/studio-cloud-bpm/studio/5.0/eclipse
RUN ./TIBCOBusinessStudio -initialize -noSplash -clean

# start a fresh image - in order to reduce final size
FROM centos:7.6.1810@sha256:6ae4cddb2b37f889afd576a17a5286b311dcbf10a904409670827f6f9b50065e as production
LABEL maintainer="TIBCO Software Inc" \
      image="Production" \
      copyright="(c) TIBCO Software Inc 2004 - 2019. All rights reserved."

# install required software and create studio user
RUN yum -y -q install xorg-x11-server-Xvfb.x86_64 && \
    yum -y -q install install /usr/lib64/libstdc++.so.5 && \
    yum -y -q install /usr/lib64/libgtk-x11-2.0.so.0 && \
    yum -y -q install /usr/lib64/libXtst.so.6 && \
    groupadd -r studio && useradd -r -m -g studio studio  && \
    mkdir -p /opt/TIBCO/studio-cloud-bpm  && \
    chown -R studio:studio /opt/TIBCO/studio-cloud-bpm

# copy studio installation from previous image
COPY --from=installer --chown=studio:studio /opt/TIBCO/studio-cloud-bpm /opt/TIBCO/studio-cloud-bpm

# copy the ant script with the generateRasc command
COPY --chown=studio:studio build.xml entrypoint.sh /opt/TIBCO/studio-cloud-bpm/studio/5.0/eclipse/

# WORKSPACE_DIR = the folder in which the studio workspace is to be created
# PROJECTS_DIR  = the folder in which the project to be imported is located
# EXPORTS_DIR   = the folder in which the generated RASC is to be written
ENV WORKSPACE_DIR=/workspace \
    PROJECTS_DIR=/projects \
    EXPORTS_DIR=/exports

RUN mkdir -p "$WORKSPACE_DIR" && chown -R studio:studio "$WORKSPACE_DIR" && chmod 777 "$WORKSPACE_DIR" && \
    mkdir -p "$PROJECTS_DIR"  && chown -R studio:studio "$PROJECTS_DIR"  && chmod 777 "$PROJECTS_DIR"   && \
    mkdir -p "$EXPORTS_DIR"   && chown -R studio:studio "$EXPORTS_DIR"   && chmod 777 "$EXPORTS_DIR"

# allow user to supply their own folders (workspace is optional)
VOLUME $WORKSPACE_DIR $PROJECTS_DIR $EXPORTS_DIR

USER studio:studio
WORKDIR /opt/TIBCO/studio-cloud-bpm/studio/5.0/eclipse

# run the ant script
ENTRYPOINT [ "sh", "./entrypoint.sh" ]