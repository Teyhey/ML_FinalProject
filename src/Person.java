
public class Person {
	String name;
	long followerCount;
	long followingCount;
	long listedCount;
	double mentionsRecieved;
	double retweetsRecieved;
	double mentionsSent;
	double retweetsSent;
	double postsCount;
	double networkFeature1;
	double networkFeature2;
	double networkFeature3;
	double inflentialProb;
	
	
	public Person(String name, long followerCount, long followingCount, long listedCount, double mentionsRecieved,
			double retweetsRecieved, double mentionsSent, double retweetsSent, double postsCount,
			double networkFeature1, double networkFeature2, double networkFeature3) {
		super();
		this.name = name;
		this.followerCount = followerCount;
		this.followingCount = followingCount;
		this.listedCount = listedCount;
		this.mentionsRecieved = mentionsRecieved;
		this.retweetsRecieved = retweetsRecieved;
		this.mentionsSent = mentionsSent;
		this.retweetsSent = retweetsSent;
		this.postsCount = postsCount;
		this.networkFeature1 = networkFeature1;
		this.networkFeature2 = networkFeature2;
		this.networkFeature3 = networkFeature3;
		this.inflentialProb = 0;
	} // constructor


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public long getFollowerCount() {
		return followerCount;
	}


	public void setFollowerCount(long followeCount) {
		this.followerCount = followeCount;
	}


	public long getFollowingCount() {
		return followingCount;
	}


	public void setFollowingCount(long followingCount) {
		this.followingCount = followingCount;
	}


	public long getListedCount() {
		return listedCount;
	}


	public void setListedCount(long listedCount) {
		this.listedCount = listedCount;
	}


	public double getMentionsRecieved() {
		return mentionsRecieved;
	}


	public void setMentionsRecieved(double mentionsRecieved) {
		this.mentionsRecieved = mentionsRecieved;
	}


	public double getRetweetsRecieved() {
		return retweetsRecieved;
	}


	public void setRetweetsRecieved(double retweetsRecieved) {
		this.retweetsRecieved = retweetsRecieved;
	}


	public double getMentionsSent() {
		return mentionsSent;
	}


	public void setMentionsSent(double mentionsSent) {
		this.mentionsSent = mentionsSent;
	}


	public double getRetweetsSent() {
		return retweetsSent;
	}


	public void setRetweetsSent(double retweetsSent) {
		this.retweetsSent = retweetsSent;
	}


	public double getPostsCount() {
		return postsCount;
	}


	public void setPostsCount(double postsCount) {
		this.postsCount = postsCount;
	}


	public double getNetworkFeature1() {
		return networkFeature1;
	}


	public void setNetworkFeature1(double networkFeature1) {
		this.networkFeature1 = networkFeature1;
	}


	public double getNetworkFeature2() {
		return networkFeature2;
	}


	public void setNetworkFeature2(double networkFeature2) {
		this.networkFeature2 = networkFeature2;
	}


	public double getNetworkFeature3() {
		return networkFeature3;
	}


	public void setNetworkFeature3(double networkFeature3) {
		this.networkFeature3 = networkFeature3;
	}
	
	
	
	
	
	

	
}
