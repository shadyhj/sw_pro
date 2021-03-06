package G05_Kruskal;

// 문제 : N, M이 주어졌을 때 MST를 구하여라 > output : 159

import java.util.*;
import java.io.*;

// * MST 구하는 기본 문제 > 반복
public class G05_Kruskal {
	static class Node implements Comparable<Node> {
		int from, to, cost;

		public Node(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node other) {
			if (this.cost < other.cost) {
				return -1;
			}
			return 1;
		}
	}

	static int N, M;	// 노드 수, 간선 수
	static int root[];	// 조상, 최종 Union할 최고조상, root 배열
	static ArrayList<Node> graph;	// * 간선연결 그냥 1차원 Node면 가능함
	static int ans;	// 답

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("src/G05_Kruskal/G05_Kruskal.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// SPTE01. 조상배열에 노드 자신의 값을 모두 넣어줌
		root = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			root[i] = i; // root[i] = 1로 해서 오류남
		}
		
		// STEP02. 간선 표현
		// graph에 들어가는 Node 클래스는 클래스에서 Comparable 해준다
		graph = new ArrayList<Node>();

		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			int f = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			graph.add(new Node(f, t, c)); // ** 크루스칼은 양방향!임
			graph.add(new Node(t, f, c));
		}

		// STEP03. cost 오름차순으로 정렬
		Collections.sort(graph);

		ans = 0;
		
		// STEP04. 최적 간선, MST 구하기
		for (int i = 0; i < graph.size(); i++) {	// * graph size 확 
			int from = graph.get(i).from;
			int to = graph.get(i).to;
			int cost = graph.get(i).cost;

			// 사이클이 발생하지 않는 경우만(서로소일 때) 간선을 이어줌
			// 간선을 이어주면서 cost의 값을 구할 수 있음
			// 최적의 MST 값
			if (findRoot(from) != findRoot(to)) {	// 서로소일 때
				Union(from, to);					// Union
				ans += cost;						// 간선 이어주며 cost값 구함
			}
		}

		System.out.println(ans);	// 최종값

	}

	private static int findRoot(int x) {
		// 기저조건 : x가 루트 조상인 경우 x 리턴
		if (x == root[x]) {
			return x;
		// x 가 루트 조상이 아닌경우 root[x]가 루트조상이 될 때까지 재귀
		} else {
			root[x] = findRoot(root[x]);
			return root[x];
		}

	}

	private static void Union(int a, int b) {
		a = findRoot(a);
		b = findRoot(b);

		if (a < b) { // b의 조상이 더 크면
			root[b] = a; // b의 조상에 작은 값을 씌움
		} else { // a의 조상이 더 크면
			root[a] = b; // a의 조상에 작은 값을 씌움
		}

	}

}
