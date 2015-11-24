package com.stacksync.syncservice.db.infinispan.models;

import com.stacksync.commons.models.ItemMetadata;

import java.io.Serializable;
import java.time.Instant;
import java.util.*;

// @Distributed
public class ItemMetadataRMI implements Serializable {

   private static final long serialVersionUID = -2494445120408291949L;

   private static Random random = new Random(System.currentTimeMillis());

  // @Key
   public Long id;
   private Long tempId;
   private Long version;
   private UUID deviceId;
   private UUID workspaceId;
   private Long parentId;
   private Long parentVersion;

   private String status;
   private Date modifiedAt;
   private Long checksum;
   private List<String> chunks;
   private Long size;
   private Boolean isFolder;
   private String filename;
   private String mimetype;

   private Integer level; // for API calls
   private Boolean isRoot; // for API calls
   private List<ItemMetadataRMI> children;

   public ItemMetadataRMI() {
      this(random.nextLong(), new Long(1), null, null,
            null, null, Date.from(Instant.now()), null,
            null, false, null, null,
            new ArrayList<String>());
   }

   public ItemMetadataRMI(Long id, Long version, UUID deviceId, Long parentId,
         Long parentVersion, String status, Date modifiedAt, Long checksum,
         Long size, boolean isFolder, String filename, String mimetype,
         List<String> chunks) {
      this.id = id;
      this.version = version;
      this.deviceId = deviceId;
      this.parentId = parentId;
      this.parentVersion = parentVersion;

      this.status = status;
      this.modifiedAt = modifiedAt;
      this.checksum = checksum;
      this.size = size;
      this.isFolder = isFolder;
      this.filename = filename;
      this.mimetype = mimetype;

      this.isRoot = false;
      this.chunks =  (chunks == null ? new ArrayList<String>() : chunks);
   }

   public ItemMetadataRMI(ItemMetadata metadata) {
      this.id = metadata.getId();
      this.version = metadata.getVersion();
      this.deviceId = metadata.getDeviceId();
      this.parentId = metadata.getParentId();
      this.parentVersion = metadata.getParentVersion();

      this.status = metadata.getStatus();
      this.modifiedAt = metadata.getModifiedAt();
      this.checksum = metadata.getChecksum();
      this.size = metadata.getSize();
      this.isFolder = metadata.isFolder();
      this.filename = metadata.getFilename();
      this.mimetype = metadata.getMimetype();

      this.isRoot = false;

      if (chunks == null) {
         this.chunks = new ArrayList<String>();
      }
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getTempId() {
      return tempId;
   }

   public void setTempId(Long tempId) {
      this.tempId = tempId;
   }

   public Long getVersion() {
      return version;
   }

   public void setVersion(Long version) {
      this.version = version;
   }

   public UUID getDeviceId() {
      return deviceId;
   }

   public void setDeviceId(UUID deviceId) {
      this.deviceId = deviceId;
   }

   public UUID getWorkspaceId() {
      return workspaceId;
   }

   public void setWorkspaceId(UUID workspaceId) {
      this.workspaceId = workspaceId;
   }

   public Long getParentId() {
      return parentId;
   }

   public void setParentId(Long parentId) {
      this.parentId = parentId;
   }

   public Long getParentVersion() {
      return parentVersion;
   }

   public void setParentVersion(Long parentVersion) {
      this.parentVersion = parentVersion;
   }

   public String getStatus() {
      return status;
   }

   public void setStatus(String status) {
      this.status = status;
   }

   public Date getModifiedAt() {
      return modifiedAt;
   }

   public void setModifiedAt(Date modifiedAt) {
      this.modifiedAt = modifiedAt;
   }

   public Long getChecksum() {
      return checksum;
   }

   public void setChecksum(Long checksum) {
      this.checksum = checksum;
   }

   public List<String> getChunks() {
      return chunks;
   }

   public void setChunks(List<String> chunks) {
      this.chunks = chunks;
   }

   public Long getSize() {
      return size;
   }

   public void setSize(Long size) {
      this.size = size;
   }

   public Boolean isFolder() {
      return isFolder;
   }

   public void setIsFolder(Boolean isFolder) {
      this.isFolder = isFolder;
   }

   public String getFilename() {
      return filename;
   }

   public void setFilename(String filename) {
      this.filename = filename;
   }

   public String getMimetype() {
      return mimetype;
   }

   public void setMimetype(String mimetype) {
      this.mimetype = mimetype;
   }

   public Integer getLevel() {
      return level;
   }

   public void setLevel(Integer level) {
      this.level = level;
   }

   public Boolean isRoot() {
      return isRoot;
   }

   public void setIsRoot(Boolean isRoot) {
      this.isRoot = isRoot;
   }

   public List<ItemMetadataRMI> getChildren() {
      if (this.children == null) {
         return new ArrayList<ItemMetadataRMI>();
      }

      return this.children;
   }

   public void setChildren(List<ItemMetadataRMI> children) {
      this.children = children;
   }

   public void addChild(ItemMetadataRMI itemMetadata) {
      if (this.children == null) {
         this.children = new ArrayList<ItemMetadataRMI>();
      }
      this.children.add(itemMetadata);
   }

   public boolean equals(ItemMetadataRMI metadata) {

      if (!metadata.getId().equals(this.id)
            || !metadata.getDeviceId().equals(this.deviceId)
            || !metadata.getChecksum().equals(this.checksum)
            || !metadata.getSize().equals(this.size)) {
         return false;
      }

      if (!metadata.getStatus().equals(this.status)
            || !metadata.getFilename().equals(this.filename)
            || !metadata.getMimetype().equals(this.mimetype)) {
         return false;
      }

      if (!metadata.getVersion().equals(this.version)) {
         return false;
      }

      if (!Objects.equals(metadata.isFolder(), this.isFolder)) {
         return false;
      }

      if (this.parentId != null && metadata.getParentId() != null) {
         if (!metadata.getParentId().equals(this.parentId)) {
            return false;
         }
      }

      if (this.parentVersion != null && metadata.getParentVersion() != null) {
         if (!metadata.getParentVersion().equals(this.parentVersion)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (obj == this) {
         return true;
      }
      if (obj.getClass() != getClass()) {
         return false;
      }

      return Objects.equals(((ItemMetadataRMI) obj).id, this.id);
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 97 * hash + Objects.hashCode(this.id);
      return hash;
   }

   @Override
   public String toString() {

      String format = "ItemMetadata: {id=%s, filename=%s, chunks=%s, content=%s}";
      String result = String.format(
            format, id==null ? "null":id,
            filename==null ? "null" : filename,
            chunks == null ? "0" : chunks.size(),
            children == null ? 0 : children.size());

      return result;
   }

   public ItemMetadata toMetadataItem() {
      ItemMetadata ret = new ItemMetadata(
            id,version,deviceId,parentId,
            parentVersion,status,modifiedAt,checksum,size,isFolder,filename,mimetype,chunks);
      return ret;
   }

   // Helpers

   public static ItemMetadataRMI createItemMetadataFromItemAndItemVersion(ItemRMI item, ItemVersionRMI itemVersion) {
      return createItemMetadataFromItemAndItemVersion(item, itemVersion, false);
   }

   public static ItemMetadataRMI createItemMetadataFromItemAndItemVersion(ItemRMI item, ItemVersionRMI itemVersion, Boolean includeChunks) {
      ArrayList<String> chunks = new ArrayList<>();
      if (includeChunks) {
         for (ChunkRMI chunk : itemVersion.getChunks()) {
            chunks.add(chunk.toString());
         }
      } else {
         chunks = null;
      }
      ItemMetadataRMI ret = new ItemMetadataRMI(item.getId(), itemVersion.getVersion(),
            itemVersion.getDevice()==null ? null : itemVersion.getDevice().getId(), item.getParentId(), item.getClientParentFileVersion(),
            itemVersion.getStatus(), itemVersion.getModifiedAt(), itemVersion.getChecksum(),
            itemVersion.getSize(), item.isFolder(), item.getFilename(), item.getMimetype(), chunks);
      ret.setWorkspaceId(item.getWorkspace().getId());
      return ret;
   }

}