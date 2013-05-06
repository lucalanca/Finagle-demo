package com.twitter.example.pages


import com.twitter.util.Future
import org.apache.thrift.protocol.TBinaryProtocol
import com.twitter.finagle.builder.{ClientBuilder, ServerBuilder}
import com.twitter.finagle.thrift.{ThriftClientFramedCodec, ThriftServerFramedCodec}
import java.net.{SocketAddress, InetSocketAddress}
import com.twitter.finagle.http.Http
import com.twitter.finagle.{Client, Server}
import com.twitter.mydemo.renamed.ModuleService

object PagesRepository {

}
