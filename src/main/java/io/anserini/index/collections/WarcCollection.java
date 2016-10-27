package io.anserini.index.collections;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import io.anserini.document.Indexable;
import io.anserini.document.WarcRecord;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashSet;
import java.util.zip.GZIPInputStream;

public class WarcCollection<D extends WarcRecord> extends Collection {
  protected DataInputStream inStream;

  public WarcCollection() throws IOException {
    super();
    allowedFileSuffix = new HashSet<>(Arrays.asList(".warc.gz"));
    skippedDirs = new HashSet<>(Arrays.asList("OtherData"));
  }

  @Override
  public void prepareInput(Path curInputFile) throws IOException {
    this.curInputFile = curInputFile;
    this.inStream = new DataInputStream(
            new GZIPInputStream(Files.newInputStream(curInputFile, StandardOpenOption.READ)));
  }

  @Override
  public void finishInput() throws IOException {
    at_eof = false;
    if (inStream != null)
      inStream.close();
  }

  @Override
  public Indexable next() {
    // should be implemented by subclass
    return null;
  }
}
