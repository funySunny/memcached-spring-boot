/**
 * Copyright 2016-2020 Sixhours
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.sixhours.memcached.cache;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * {@code XMemcached} memcached client implementation.
 *
 * @author Igor Bolic
 */
public class XMemcachedClient implements IMemcachedClient {

    private final MemcachedClient memcachedClient;

    public XMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    @Override
    public Object nativeCache() {
        return this.memcachedClient;
    }

    @Override
    public Object get(String key) {
        try {
            return this.memcachedClient.get(key);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new MemcachedOperationException("Failed to get key", e);
        }
    }

    @Override
    public void set(String key, int exp, Object value) {
        try {
            this.memcachedClient.set(key, exp, value);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new MemcachedOperationException("Failed to set key", e);
        }
    }

    @Override
    public void touch(String key, int exp) {
        try {
            this.memcachedClient.touch(key, exp);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new MemcachedOperationException("Failed to touch key", e);
        }
    }

    @Override
    public void delete(String key) {
        try {
            this.memcachedClient.delete(key);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new MemcachedOperationException("Failed to delete key", e);
        }
    }

    @Override
    public void flush() {
        try {
            this.memcachedClient.flushAll();
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new MemcachedOperationException("Failed to flush all keys", e);
        }
    }

    @Override
    public long incr(String key, int by) {
        try {
            return this.memcachedClient.incr(key, by);
        } catch (TimeoutException | InterruptedException | MemcachedException e) {
            throw new MemcachedOperationException("Failed to increment key", e);
        }
    }

    @Override
    public void shutdown() {
        try {
            this.memcachedClient.shutdown();
        } catch (IOException e) {
            throw new MemcachedOperationException("Failed to shutdown client", e);
        }
    }
}